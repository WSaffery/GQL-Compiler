package gql_gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import ast.GqlProgram;
import ast.atoms.Quantifier;
import ast.expressions.Expression;
import ast.expressions.Value;
import ast.expressions.atomic.TruthValue;
import ast.expressions.composite.BooleanConjunctionExpression;
import ast.expressions.composite.ComparisonExpression;
import ast.expressions.composite.NegatedExpression;
import ast.expressions.graph.GraphExistsExpression;
import ast.expressions.references.NameExpression;
import ast.expressions.references.PropertyReference;
import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.NodePattern;
import ast.patterns.ParenPathPattern;
import ast.patterns.PathComponent;
import ast.patterns.PathPattern;
import ast.patterns.QualifiedPathPattern;
import ast.patterns.label.BinaryLabelExpression;
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import ast.patterns.label.LabelPattern;
import ast.patterns.label.WildcardLabel;
import ast.queries.GqlQuery;
import ast.returns.Asterisk;
import ast.returns.CountAsterisk;
import ast.returns.ReturnExpression;
import ast.returns.ReturnItem;
import enums.BooleanComparator;
import enums.EvaluationMode;
import enums.SetQuantifier;
import enums.ValueComparator;
import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import gql_gremlin.matching.MatchExpression;
import gql_gremlin.matching.MatchPatternFactory;
import groovyjarjarantlr4.v4.misc.Graph;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static gql_gremlin.helpers.GremlinHelpers.*;
import static gql_gremlin.helpers.JavaHelpers.*;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;


enum GQLDirection {
    UNDIRECTED,
    RIGHT_TO_LEFT,
    LEFT_TO_RIGHT
}

record PropertyResult(
    String propertyKey,
    String alias
)
{}


class CompilerHelpers
{
    // reorders restricted path patterns so that for each of the contained connected components
    // its path patterns are contiguous in the resulting list and in the order of some dfs through the component
    //
    // i.e. [(a -> b), (d -> a), (b -> c), (c -> d)] becomes [(a -> b), (b -> c), (c -> d), (d -> a)]
    // note this function doesn't care about the direction of edges in patterns, assumption being querying is bidirectional
    public static List<QualifiedPathPattern> orderRestrictedPathPatterns(List<QualifiedPathPattern> restrictedPathPatterns)
    {
        ArrayList<QualifiedPathPattern> reorderedPathPatterns = new ArrayList<>();

        List<HashSet<String>> variablesList = 
            restrictedPathPatterns.stream().map(pathPattern -> pathPatternVariables(pathPattern)).toList();

        
        while (restrictedPathPatterns.size() > 0)
        {
            int i = restrictedPathPatterns.size()-1;

            while (i != -1) 
            {
                reorderedPathPatterns.add(restrictedPathPatterns.remove(i));
                Set<String> variables = variablesList.remove(i);
                i = -1;

                for (int j = 0; j < restrictedPathPatterns.size(); j++)
                {
                    Set<String> testVariables = variablesList.get(j);
                    if (intersects(variables, testVariables))
                    {
                        i = j;
                        break;
                    }
                }
            }
        }

        return reorderedPathPatterns;
    }
    
    public static Stream<String> pathPatternVariablesAllLayersStream(List<PathComponent> pathSequence)
    {
        return pathSequence.stream().flatMap(component -> {
            Stream<String> stream = null;
            if (component instanceof ParenPathPattern)
            {
                stream = pathPatternVariablesAllLayersStream(((ParenPathPattern) component).pathPattern().pathSequence());
            }
            else 
            {
                stream = Stream.ofNullable(((ElementPattern) component).variableName.orElse(null));
            }
            return stream;
        });
    }

    public static HashSet<String> pathPatternVariablesAllLayers(List<PathComponent> pathSequence)
    {
        return new HashSet<>(pathPatternVariablesAllLayersStream(pathSequence).toList());
    }

    public static HashSet<String> pathPatternVariables(List<PathComponent> pathSequence)
    {
        return new HashSet<>(pathSequence.stream().flatMap(component -> {
            Stream<String> stream = null;
            if (component instanceof ParenPathPattern)
            {
                stream = Stream.of();
            }
            else 
            {
                stream = Stream.ofNullable(((ElementPattern) component).variableName.orElse(null));
            }
            return stream;
        }).toList());
    }

    public static HashSet<String> pathPatternVariables(QualifiedPathPattern pathPattern)
    {
        return pathPatternVariables(pathPattern.pathPattern().pathSequence());
    }

}

// essentially takes the place of GqlFileQueryEvaluator
public class GremlinCompiler {

    // left deep nesting of and operation to get around inability to access an array of graph traversals
    public GraphTraversal<Object, Object> recurseAndOperands(List<LabelExpression> operands)
    {
        if (operands.size() == 1) return filterByLabelExpression(operands.get(0));
        return and(filterByLabelExpression(operands.get(0)), recurseAndOperands(operands.subList(1, operands.size())));
    }

    // left deep nesting of or operation to get around inability to access an array of graph traversals
    public GraphTraversal<Object, Object> recurseOrOperands(List<LabelExpression> operands)
    {
        if (operands.size() == 1) return filterByLabelExpression(operands.get(0));
        return or(filterByLabelExpression(operands.get(0)), recurseAndOperands(operands.subList(1, operands.size())));
    }

    public GraphTraversal<Object, Object> filterByLabelExpression(LabelExpression labelExpression)
    {
        if (labelExpression instanceof Label)
        {
            Label label = (Label) labelExpression;
            return hasLabel(label.getValue());
        }
        else if (labelExpression instanceof BinaryLabelExpression)
        {
            final BinaryLabelExpression binaryExpression = (BinaryLabelExpression) labelExpression;
            List<LabelExpression> operands = binaryExpression.operandExpressions();    
            // @SuppressWarnings("unchecked")
            // GraphTraversal<Object,Object>[] subTraversals = (GraphTraversal<Object,Object>[]) operands.stream().map((operand) -> filterByLabelExpression(operand)).toArray();

            switch (binaryExpression.operator())
            {
                case AND:                   
                    return recurseAndOperands(operands);

                case OR:
                    return recurseOrOperands(operands);
            }
        }
        throw new SemanticErrorException("Bad label expression");
    }

    public <S, T> GraphTraversal<S, T> filterByPattern(GraphTraversal<S, T> traversal, ElementPattern pattern)
    {

        final LabelPattern labelPattern = pattern.labelPattern();

        if (labelPattern != null) 
        {         
            if (labelPattern instanceof WildcardLabel)
            {
                // simply don't filter
            }
            else if (labelPattern instanceof LabelExpression)
            {
                final LabelExpression labelExpression = (LabelExpression) labelPattern;
                if (labelExpression instanceof Label)
                {
                    traversal.hasLabel(((Label) labelExpression).getValue());
                }
                else 
                {
                    traversal = traversal.and(filterByLabelExpression(labelExpression));    
                }
            }
            else 
            {
                throw new SemanticErrorException("Unsupported label expression");
            }
        }

        for (Entry<String, Value> item : pattern.properties().entrySet())
        {
            String key = item.getKey();
            Object value = item.getValue().getValue();
            // continue;
            if (value instanceof Float)
            {
                Float num = (Float) value;
                traversal = traversal.has(key, num);
            }
            else if (value instanceof String)
            {
                String str = (String) value;
                traversal = traversal.has(key, str);
            }
            else if (value instanceof Boolean)
            {
                Boolean bool = (Boolean) value;
                traversal = traversal.has(key, bool);
            }
            else 
            {
                System.out.println("Property Value of type " + value.getClass().getName() + " is not supported");
            }
            
        }

        return traversal;
    }

    public <E> GraphTraversal<Vertex, E> compileElementPattern(GraphTraversal<Vertex, E> traversal, ElementPattern pattern, HashSet<String> capturedSet)
    {
        Optional<String> variableName = pattern.variableName();
        boolean captured = variableName.isPresent() && capturedSet.contains(variableName.get());

        if (captured) 
        {
            traversal = traversal.where(P.eq(variableName.get()));
        }

        traversal = filterByPattern(traversal, pattern);

        if (!captured && variableName.isPresent())
        {
            traversal = traversal.as(variableName.get());
            capturedSet.add(variableName.get());
        }

        return traversal;
    }

    // if the element has a variable
        // if the variable is in the outerCapturedSet join on it
        // otherwise capture it and add it to captured set
    public <E> GraphTraversal<Vertex, E> compileElementPattern(
        GraphTraversal<Vertex, E> traversal, ElementPattern pattern, 
        Set<String> capturedSet, Set<String> outerCapturedSet)
    {

        Optional<String> variableName = pattern.variableName();
        boolean capturedPrior = variableName.isPresent() && outerCapturedSet.contains(variableName.get());


        if (capturedPrior) 
        {
            traversal = traversal.where(P.eq(variableName.get()));
        }

        traversal = filterByPattern(traversal, pattern);

        if (!capturedPrior && variableName.isPresent())
        {
            traversal = traversal.as(variableName.get());
            capturedSet.add(variableName.get());
        }

        return traversal;
    }

    public GraphTraversal<Vertex, Vertex> compileStartingElementPattern(GraphTraversal<Vertex, Vertex> traversal, 
        ElementPattern pattern, HashSet<String> capturedSet)
    {
        Optional<String> variableName = pattern.variableName();
        boolean captured = variableName.isPresent() && capturedSet.contains(variableName.get());

        if (captured) 
        {
            traversal = traversal.select(variableName.get());
        }
        else if (pattern instanceof NodePattern)
        {
            traversal = traversal.V();
        }
        else if (pattern instanceof EdgePattern)
        {
            throw new SemanticErrorException("Can't start pattern with edge");
            // traversal = traversal.E();
        }

        traversal = filterByPattern(traversal, pattern);

        if (!captured && variableName.isPresent())
        {
            traversal = traversal.as(variableName.get());
            capturedSet.add(variableName.get());
        }

        return traversal;
    }

    // outer captured set is variables from outside the parametrised path
    // these variables need to be joined on (using where)
    // other variables encountered need to be captured in a group (using as)
    public GraphTraversal<Vertex, Vertex> compileRestrictedPatternContinued(GraphTraversal<Vertex, Vertex> traversal, List<PathComponent> components, Set<String> outerCapturedSet)
    {
        HashSet<String> capturedSet = new HashSet<>();
        HashSet<String> variables = CompilerHelpers.pathPatternVariables(components);

        GraphTraversal<Vertex, ?> intermediary = null;

        for (PathComponent component : components)
        {
            if (component instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) component;
                // can use labels parameter in toE/bothE, but hasLabels is added in filterByPattern anyway
                if (edgePattern.direction.isPresent())
                {
                    intermediary = traversal.toE(toGremlinDirection(edgePattern.direction.get()));
                    intermediary = compileElementPattern(intermediary, edgePattern, capturedSet, outerCapturedSet);
                    traversal = intermediary.otherV();
                }
                else 
                {
                    intermediary = traversal.bothE();
                    intermediary = compileElementPattern(intermediary, edgePattern, capturedSet, outerCapturedSet);
                    traversal = intermediary.otherV();
                }   
            }
            else if (component instanceof NodePattern)
            {
                NodePattern nodePattern = (NodePattern) component;
                traversal = compileElementPattern(traversal, nodePattern, capturedSet, outerCapturedSet);
            }
            else { // instance of ParenPathPattern
                ParenPathPattern parenPathPattern = (ParenPathPattern) component;
                traversal = compileParenPathPattern(traversal, parenPathPattern, capturedSet, variables);
            }
        }

        return traversal;
    }

    public GraphTraversal<Vertex, Vertex> compileParenPathPattern(GraphTraversal<Vertex, Vertex> traversal, 
        ParenPathPattern parenPathPattern, Set<String> capturedVariables, Set<String> outerVariables)
    {
        List<PathComponent> parenComponents = parenPathPattern.pathPattern().pathSequence();
        
        // all variables in the outer path that are currently uncaptured- that come after the paren path
        HashSet<String> uncapturedOuterVariables = subtraction(outerVariables, capturedVariables);
        
        // variables shared by this paren path and the outer path that come after this paren path
        HashSet<String> uncapturedInnerVariables = null;
        // potentially expensive inner variable calculation, avoid if possible
        if (uncapturedOuterVariables.size() > 0)
        {
            HashSet<String> innerVariables = CompilerHelpers.pathPatternVariablesAllLayers(parenComponents);
            uncapturedInnerVariables = intersection(uncapturedOuterVariables, innerVariables);
        }
        else 
        {
            uncapturedInnerVariables = new HashSet<>();
        }

        Quantifier quantifier = parenPathPattern.quantifier();
        boolean uncaptured = uncapturedInnerVariables.size() > 0; 
        boolean unroll =  quantifier.b() > 1; 

        // TODO! add support for 0 length iteration
        if (uncaptured && unroll)
        {
            // if the iteration is degenerate (a = 1, b = 1), don't include captured post variables in outerSet    
            // just add them to the current captured set and continue after adding the sub-traversal
                // see uncaptured && !unroll case below

            // assume quantifier is valid (0 < a <= b)
            // must unroll the paren path by one, to instantiate these uncaptured variables as singletons
            
            quantifier = new Quantifier(Integer.max(1, quantifier.a()-1), quantifier.b()-1);
            
            traversal = compileRestrictedPatternContinued(traversal, parenComponents, capturedVariables);
            // we've unrolled by one, joining on prior captured variables in capturedSet, 
            // and instantiating the uncaptured variables in uncapturedVariables
            capturedVariables.addAll(uncapturedInnerVariables);
        }

        traversal = traversal.
            repeat(compileRestrictedPatternContinued(start(), parenComponents, capturedVariables)).
            times(quantifier.b()).
            emit(loops().is(P.gte(quantifier.a())));

        if (uncaptured && !unroll)
        {
            capturedVariables.addAll(uncapturedInnerVariables);
        }

        return traversal;
    }

    public GraphTraversal<Vertex, Vertex> compileRestrictedPattern(GraphTraversal<Vertex, Vertex> traversal, QualifiedPathPattern qualifiedPathPattern, HashSet<String> capturedSet)
    {
        GraphTraversal<Vertex, ?> temp = null; // used for intermediary steps

        PathPattern pathPattern = qualifiedPathPattern.pathPattern();
        List<PathComponent> components = pathPattern.pathSequence();
        HashSet<String> variables = CompilerHelpers.pathPatternVariables(components);

        // !TODO: fix path compiler labels
        //  - are they even used?
        //  - using the hash code may be unsound, what if multiple paths were identical
        //      - depends on how records generate hash codes when they have object components
        String pathHashCode = Integer.toString(qualifiedPathPattern.hashCode());
        String startLabel = "#COMPILER_s%s".formatted(pathHashCode);
        String endLabel = "#COMPILER_e%s".formatted(pathHashCode);
        

        PathComponent head = components.get(0);
        if (head instanceof ElementPattern)
        {
            traversal = compileStartingElementPattern(traversal, (ElementPattern) head, capturedSet);
            traversal = traversal.as(startLabel);
        }
        else 
        {
            // throw new SyntaxErrorException("Paren path pattern execution as head of path currently unsupported");
            // need to unroll one layer 
            // and start from there if possible

            GraphTraversal<Vertex, Vertex> startingTraversal = traversal.V();
            startingTraversal = startingTraversal.as(startLabel);
            traversal = compileParenPathPattern(
                startingTraversal, (ParenPathPattern) head, capturedSet, variables);
        }

        for (PathComponent component : components.subList(1, components.size()))
        {
            if (component instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) component;
                // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                if (edgePattern.direction.isPresent())
                {
                    temp = traversal.toE(toGremlinDirection(edgePattern.direction.get()));
                    temp = compileElementPattern(temp, edgePattern, capturedSet);
                    traversal = temp.otherV();
                }
                else 
                {
                    temp = traversal.bothE();
                    temp = compileElementPattern(temp, edgePattern, capturedSet);
                    traversal = temp.otherV();
                }
                
            }
            else if (component instanceof NodePattern)
            {
                NodePattern nodePattern = (NodePattern) component;
                traversal = compileElementPattern(traversal, nodePattern, capturedSet);
            }
            else { // instance of ParenPathPattern
                ParenPathPattern parenPathPattern = (ParenPathPattern) component;
                traversal = compileParenPathPattern(traversal, parenPathPattern, capturedSet, variables);
            }
        }


        if (qualifiedPathPattern.evaluationMode() == EvaluationMode.ACYCLIC)
        {
            traversal = traversal.simplePath().from(startLabel);
        }
        if (qualifiedPathPattern.variableName().isPresent())
        {
            traversal = traversal.as(endLabel);
            traversal = traversal.path().from(startLabel).as(qualifiedPathPattern.variableName().get()).select(endLabel);
        }

        return traversal;
    }

    public GraphTraversal<Vertex, Vertex> compileRestrictedPatterns(GraphTraversal<Vertex, Vertex> traversal, List<QualifiedPathPattern> qualifiedPathPatterns,
        HashSet<String> capturedSet)
    {

        for (QualifiedPathPattern qualifiedPathPattern : qualifiedPathPatterns)
        {
            traversal = compileRestrictedPattern(traversal, qualifiedPathPattern, capturedSet);
        }

        return traversal;
    }

    public GraphTraversal<Vertex, Vertex> compileToTraversal(
        GraphTraversal<Vertex, Vertex> start, MatchExpression matchExpression, HashSet<String> capturedSet)
    {        
        GraphTraversal<Vertex, Vertex> traversal = matchExpression.isMandatory ? 
              compileRestrictedPatterns(start, matchExpression.pathPatterns, capturedSet)
            : start.optional(compileRestrictedPatterns(start(), matchExpression.pathPatterns, capturedSet));

        if (matchExpression.whereClause.isPresent())
        {
            assert Expression.isBooleanExpression(matchExpression.whereClause.get()) : "Where clause must be boolean expression";  
            traversal = traversal.and(filterByWhereClause(matchExpression.whereClause.get()));
        }

        return traversal;
    }



    private GraphTraversal<Object, Object> filterByWhereClause(Expression expression) {
        if (expression instanceof TruthValue)
        {
            TruthValue truthValue = (TruthValue) expression;
            Boolean value = truthValue.value();
            // traversal.and();
            throw new SyntaxErrorException("Truth Value Where Clause not supported.");
        }
        else if (expression instanceof ComparisonExpression)
        {
            ComparisonExpression comp = (ComparisonExpression) expression;
            // TODO! support val comps
            assert Expression.isReferenceExpression(comp.left()) : "Only currently support reference operands of comparisons";
            assert Expression.isReferenceExpression(comp.right())  : "Only currently support reference operands of comparisons";

            if (comp.left() instanceof NameExpression && comp.right() instanceof NameExpression)
            {
                String a = ((NameExpression) comp.left()).name();
                String b = ((NameExpression) comp.right()).name();
                
                if (comp.comparator() == ValueComparator.EQ)
                {
                    return select(a).where(P.eq(b));
                }
                else if (comp.comparator() == ValueComparator.NEQ)
                {
                    return select(a).where(P.neq(b));
                }
                else 
                {
                    throw new SemanticErrorException("Can only check equality of identity references");
                }
                
            }
            else if (comp.left() instanceof PropertyReference && comp.right() instanceof PropertyReference)
            {
                PropertyReference aProp = (PropertyReference) comp.left();
                PropertyReference bProp = (PropertyReference) comp.right();
                
                String bHashCode = Integer.toString(bProp.hashCode());
                String bLabel = "#COMPILER_b%s".formatted(bHashCode);
                
                P<String> predicate = null;
                switch (comp.comparator())
                {
                    case EQ:
                        predicate = P.eq(bLabel);
                        break;
                    case NEQ:
                        predicate = P.neq(bLabel);
                        break;
                    case GEQ:
                        predicate = P.gte(bLabel);
                        break;
                    case GT:
                        predicate = P.gt(bLabel);
                        break;
                    case LEQ:
                        predicate = P.lte(bLabel);
                        break;
                    case LT:
                        predicate = P.lt(bLabel);
                        break;
                }   

                return select(bProp.name()).values(bProp.key()).as(bLabel).
                       select(aProp.name()).values(aProp.key()).
                       where(predicate);
            }
            else 
            {
                throw new SyntaxErrorException("Bad comparison expression");
            }
        }
        else if (expression instanceof GraphExistsExpression)
        {
            GraphExistsExpression graphExistsExpression = (GraphExistsExpression) expression;
            QualifiedPathPattern pathPattern = graphExistsExpression.qualifiedPathPattern();
            HashSet<String> variables = CompilerHelpers.pathPatternVariables(pathPattern);
            // TODO! check: could use where step here?
            return and(compileRestrictedPattern(start(), pathPattern, variables));
        }
        else if (expression instanceof NegatedExpression)
        {
            NegatedExpression negatedExpression = (NegatedExpression) expression;
            return not(filterByWhereClause(negatedExpression.subExpression()));
        }
        else if (expression instanceof BooleanConjunctionExpression)
        {
            BooleanConjunctionExpression conjunctionExpression = (BooleanConjunctionExpression) expression;
            switch (conjunctionExpression.comparator())
            {
                case AND:
                    return and(
                        filterByWhereClause(conjunctionExpression.left()), 
                        filterByWhereClause(conjunctionExpression.right())
                    );
                case OR:
                    return or(
                        filterByWhereClause(conjunctionExpression.left()), 
                        filterByWhereClause(conjunctionExpression.right())
                    );
                default:
                    throw new SyntaxErrorException("Only AND and OR are supported for boolean conjunction");
            }
        }
        else 
        {
            throw new RuntimeException("Unsupported Where Clause Expression");
        }
    }

    // use pop mixed for now
    // should later use variable metadata to pop all for groups and one for singles
    public static GraphTraversal<Vertex, Map<String,Object>> variadicSelect(GraphTraversal<Vertex, ?> traversal, AbstractCollection<String> selectKeys)
    {
        if (selectKeys.size() == 0) {
            return null;
        }

        String[] array = selectKeys.toArray(new String[0]);

        if (array.length == 1)
        {
            return traversal.select(Pop.mixed, array[0]).project(array[0]);
        }
        else if (array.length == 2)
        {
            return traversal.select(Pop.mixed, array[0], array[1]);
        }
        else 
        {
            // avoids using copyOfRange explicitly, does so implicitly
            String[] subarray = Arrays.asList(array).subList(2, array.length).toArray(new String[0]);
            return traversal.select(Pop.mixed, array[0], array[1], subarray);
        }
    }

    enum ReturnType {
        GraphValue,
        GraphMap,
        Value
    };

    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(GqlQuery query)
    {
        ArrayList<String> fullReturnNames = new ArrayList<>();
        ArrayList<ReturnType> returnNameTypes = new ArrayList<>();
        ArrayList<String> returnNames = new ArrayList<>();
        

 
        // reference name [element label] : {(reference key [property name], alias [value label]), ...}        
        HashMap<String, List<PropertyResult>> returnReferences = new HashMap<>();
        for (ReturnItem item : query.returnStatement.returnItems())
        {
            if (item instanceof ReturnExpression)
            {
                ReturnExpression returnExpression = (ReturnExpression) item;
                Expression expr = returnExpression.expression();
                String alias = returnExpression.name();

                if (expr instanceof NameExpression) 
                {
                    NameExpression nameExpr = (NameExpression) expr;
                    fullReturnNames.add(nameExpr.name());
                    returnNames.add(nameExpr.name());
                    returnNameTypes.add(ReturnType.GraphValue);
                }
                else if (expr instanceof PropertyReference)
                {
                    PropertyReference propertyReference = (PropertyReference) expr;
                    String referenceName = propertyReference.name();
                    String referenceKey = propertyReference.key();
                    
                    returnReferences.putIfAbsent(referenceName, new ArrayList<>());
                    returnReferences.get(referenceName).add(new PropertyResult(referenceKey, alias));

                    fullReturnNames.add(alias);
                    returnNames.add(alias);

                    if (referenceKey.equals("*"))
                    {
                        returnNameTypes.add(ReturnType.GraphMap);    
                    }
                    else 
                    {
                        returnNameTypes.add(ReturnType.Value);
                    }
                }
                else
                {
                    System.out.println("Unsupported return item");
                }
            }
            else if ((item instanceof CountAsterisk) && query.returnStatement.returnItems().size() == 1)
            {
                continue;
            }
            else {
                throw new SyntaxErrorException("Bad return syntax");
            }
        }

        GraphTraversal<Vertex, Vertex> traversal = start();
        HashSet<String> capturedSet = new HashSet<>();
        for (MatchExpression matchExpression : query.matchExpressions)
        {
            traversal = compileToTraversal(traversal, matchExpression, capturedSet);
        }

        if (query.returnStatement.setQuantifier() != SetQuantifier.ALL)
        {
            System.out.println("Distinct not currently supported");
        }

        // return everything matched
        if (query.returnStatement.returnItems().size() == 1)
        {
            if (query.returnStatement.returnItems().get(0) instanceof Asterisk)
            {
                throw new SyntaxErrorException("Asterisk return currently unsupported");
            }
            else if (query.returnStatement.returnItems().get(0) instanceof CountAsterisk)
            {
                CountAsterisk countAsterisk = (CountAsterisk) query.returnStatement.returnItems().get(0);
                String name = countAsterisk.name().orElseGet(() -> "COUNT(*)");
                GraphTraversal<Vertex, Map<String, Object>> resultTraversal =
                    traversal.count().project(name).by();
                return resultTraversal;
            }
        }

        for (Entry<String, List<PropertyResult>> elementReferences: returnReferences.entrySet())
        {
            String elementLabel = elementReferences.getKey();
            List<PropertyResult> results = elementReferences.getValue();
            traversal = traversal.select(elementLabel);
            for (PropertyResult result : results)
            {
                if (result.propertyKey().equals("*"))
                {
                    traversal.as(result.alias());
                }
                else {
                    traversal.properties(result.propertyKey());
                    traversal.as(result.alias());
                    traversal.element();
                }
            }
        }

        GraphTraversal<Vertex, Map<String, Object>> resultTraversal = variadicSelect(
            traversal, returnNames);

        for (ReturnType returnNameType : returnNameTypes)
        {
            switch (returnNameType)
            {
                case Value:
                    resultTraversal = resultTraversal.by(value());
                    break;
                case GraphMap:
                    resultTraversal = resultTraversal.by(elementMap());
                    break;
                case GraphValue:
                    resultTraversal = resultTraversal.by();
                    break;
            }
        }
        
        return resultTraversal;
    }

    // discards graphName information from focused match clauses
    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(GqlProgram program)
    {
        if (program.body.isSingular())
        {
            return compileToTraversal(program.body.getQuery(0));
        }

        ArrayList<GraphTraversal<Vertex, Map<String,Object>>> queryTraversals = new ArrayList<>();
        for (GqlQuery query : program.body.getQueries()) 
        {
            queryTraversals.add(compileToTraversal(query));
        }

        GraphTraversal<Vertex, Map<String,Object>> fullTraversal = queryTraversals.get(0);
        for (int i = 1; i < queryTraversals.size(); i++)
        {
            fullTraversal = conjoinTraversals(
                fullTraversal, 
                program.body.getConjunctor(i-1), 
                queryTraversals.get(i));
        }

        return fullTraversal;
    }
    
}
