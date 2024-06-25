package gql_gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import ast.GqlProgram;
import ast.atoms.Quantifier;
import ast.expressions.Expression;
import ast.expressions.Value;
import ast.expressions.references.NameExpression;
import ast.expressions.references.PropertyReference;
import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.NodePattern;
import ast.patterns.ParenPathPattern;
import ast.patterns.PathComponent;
import ast.patterns.PathPattern;
import ast.patterns.QualifiedPathPattern;
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import ast.patterns.label.WildcardLabel;
import ast.returns.Asterisk;
import ast.returns.ReturnExpression;
import ast.returns.ReturnItem;
import enums.EvaluationMode;
import enums.EvaluationModeCategory;
import enums.QueryConjunctor;
import enums.SetQuantifier;
import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import gql_gremlin.helpers.VariableOccurenceCounter;
import gql_gremlin.matching.MatchExpression;
import gql_gremlin.matching.MatchPatternFactory;


// import com.tinkerpop.blueprints.Direction;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static gql_gremlin.helpers.GremlinHelpers.*;

import java.lang.reflect.Constructor;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


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

// essentially takes the place of GqlFileQueryEvaluator
public class GremlinCompiler {

    @SuppressWarnings("unchecked")
    public <A, B> GraphTraversal<A, B> conjoinTraversals(GraphTraversal<A, B> x, QueryConjunctor conjunctor, GraphTraversal<A, B> y)
    {
        GraphTraversal<A, A> traversal = start();
        switch (conjunctor)
        {
            case UNION_ALL:
                return traversal.union(x, y);
            default:
                System.out.println("Only union all is currently supported");
                assert(false);
            
        }
        return null;
    }

    
    enum LastTraversed {
        V,
        E
    }

    public String[] getLabelStrings(ArrayList<ArrayList<Label>> labels)
    {
        if (labels.size() == 0)
        {
            System.out.println("Fully empty label set");
            return new String[0];
        }
        else if (labels.get(0).size() == 0)
        {
            System.out.println("Inner empty label set");
            return new String[0];
        }
        else if (labels.size() != 1 || labels.get(0).size() != 1)
        {
            System.out.println("Too many label sets");
            return null;
        }
        else 
        {
            return new String[] { labels.get(0).get(0).toString() };
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] safeToArray(List<T> list)
    {
        if (list == null)
        {
            Object[] empty = new Object[0];
            return (T[]) empty;
        }
        else {
            return (T[]) list.toArray();
        }
    }

    public <S, T> GraphTraversal<S, T> filterByPattern(GraphTraversal<S, T> traversal, ElementPattern pattern)
    {

        final LabelExpression labelExpression = pattern.labelExpression();

        if (labelExpression != null) 
        {         
            if (labelExpression instanceof Label)
            {
                Label label = (Label) labelExpression;
                traversal = traversal.hasLabel(label.getValue());
            }
            else if (labelExpression instanceof WildcardLabel)
            {
                // System.out.println("Too many labels!");
            }
            else 
            {
                throw new SemanticErrorException("Only basic label expressions are implemented");
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

    // returns a traversal that establishes an instance of the element pattern
    // at the start or end of a match pattern
    // public GraphTraversal<?, ?> asInstance(OrderedElementPattern pattern)
    // {
    //     if (pattern.variableName().isPresent())
    //     {
    //         if (pattern.preceded()) 
    //         {
    //             return where(P.eq(pattern.variableName().get()));
    //         }
    //         else 
    //         {
    //             return as(pattern.variableName().get());
    //         }
    //     }
    //     return identity();
    // }

    // Path-Based Helpers
    // In a path when matching an ordered element pattern
    // traversal = filterByPrior(traversal, orderedPattern); // filters by previous capture if pattern is preceeded
    // traversal = filterByPattern(traversal, pattern);
    // traversal = captureInstance(traversal, orderedPattern); // establishes initial capture if not preceeded



    // if the pattern's variable has been preceed, ensure we match the preceeded captured elements
    // otherwise, do nothing
    // public GraphTraversal<?, ?> filterByPrior(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
    // {
    //     if (pattern.variableName().isPresent() && pattern.preceded())
    //     {
    //         return traversal.where(P.eq(pattern.variableName().get()));
    //     }
    //     return traversal;
    // }

    // // if the pattern's variable hasn't been preceeded, capture the current elements as that variable
    // // otherwise, do nothing
    // public GraphTraversal<?, ?> captureInstance(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
    // {
    //     if (pattern.variableName().isPresent() && !pattern.preceded())
    //     {
    //         return traversal.as(pattern.variableName().get());
    //     }
    //     return traversal;
    // }

    // Match-Based Helpers
    // in a match when matching an element pattern

    // Note match is truly stateless and doesn't need to use Ordered patterns, kept as is for now
    public GraphTraversal<?, ?> asInstance(ElementPattern pattern)
    {
        if (pattern.variableName().isPresent())
        {
            return as(pattern.variableName().get());
        }
        return identity();
    }

    public <S, T> GraphTraversal<S, T> asInstance(GraphTraversal<S, T> traversal, ElementPattern pattern)
    {
        if (pattern.variableName().isPresent())
        {
            return traversal.as(pattern.variableName().get());
        }
        return traversal.identity();
    }

    // in a match statement we always use as to capture and instance rather than where if it's preceeded
    // this is because match will match against variables previously created using the traverser's path history
    // public GraphTraversal<?, ?> captureMatchInstance(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
    // {
    //     if (pattern.variableName().isPresent())
    //     {
    //         return traversal.as(pattern.variableName().get());
    //     }
    //     return traversal;
    // }

    // we need to use where if we're not splitting on anything with more than one reference outside of unrestricted
    
    // every
    // public GraphTraversal<?, ?> compileToTraversal(MatchPattern matchPattern)
    // {
    //     GraphTraversal<?, ?> traversal = null;

    //     OrderedElementPattern head = matchPattern.headPattern();
    //     OrderedElementPattern tail = matchPattern.tailPattern();

    //     traversal = asInstance(head);

    //     traversal = filterByPattern(traversal, head.pattern());
    //     if (head.pattern() instanceof EdgePattern)
    //     {
    //         EdgePattern headEdgePattern = (EdgePattern) head.pattern();
    //         traversal = traversal.toV(toGremlinDirection(headEdgePattern.direction).opposite());
    //     }
    //     else
    //     {
    //         assert(head.pattern() instanceof NodePattern);
    //     }

    //     for (OrderedElementPattern orderedPattern : matchPattern.middlePatterns())
    //     {
    //         // assert(pattern.variableName == null);
    //         final ElementPattern pattern = orderedPattern.pattern();
    //         if (pattern instanceof EdgePattern) 
    //         {
    //             EdgePattern edgePattern = (EdgePattern) pattern;
    //             // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
    //             traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
    //             traversal = filterByPattern(traversal, edgePattern);
    //             traversal = captureMatchInstance(traversal, orderedPattern);
    //             traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
    //         }
    //         else if (pattern instanceof NodePattern)
    //         {
    //             NodePattern nodePattern = (NodePattern) pattern;
    //             traversal = filterByPattern(traversal, nodePattern);
    //             traversal = captureMatchInstance(traversal, orderedPattern);
    //         }
    //         else 
    //         {
    //             System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
    //         }
    //     }

        
    //     if (tail.pattern() instanceof EdgePattern)
    //     {
    //         EdgePattern tailEdgePattern = (EdgePattern) tail.pattern();
    //         traversal = traversal.toE(toGremlinDirection(tailEdgePattern.direction));
    //     }
    //     else
    //     {
    //         assert(tail.pattern() instanceof NodePattern);
    //     }

    //     traversal = filterByPattern(traversal, tail.pattern());
    //     traversal = captureMatchInstance(traversal, tail); 
    //     // will be joined against other patterns with the same variable in the match statement

    //     System.out.println("(0) match traversal: " + traversal.explain().prettyPrint());
    //     return traversal;
    // }

    // public GraphTraversal<?, ?> compileToSimpleTraversal(GraphTraversal<?, ?> start, List<OrderedElementPattern> matchPattern)
    // {
        
    //     return start;
    // }


    public GraphTraversal<?, ?> compileToMatchTraversal(List<ElementPattern> matchPattern)
    {
        ElementPattern head = matchPattern.get(0);
        ElementPattern tail = matchPattern.get(matchPattern.size()-1);

        // Although we can determine the eventual type parameters <S,E> of our traversal 
        // from head and tail, we can only do this at runtime. Furthermore graph traversal's
        // type parameter mutates over the course of building the traversal and starts 
        // inevitably as <S,S> rather than <S,E>.
        // As such we must take parameters ?,? to make the variable valid throughout the process
        // of building the final traversal. Even if we could somehow set the parameters at runtime.
        
        GraphTraversal<?, ?> traversal = asInstance(head);
        traversal = filterByPattern(traversal, head);

        if (head instanceof EdgePattern)
        {
            EdgePattern headEdgePattern = (EdgePattern) head;
            traversal = traversal.toV(toGremlinDirection(headEdgePattern.direction).opposite());
        }

        for (int i = 1; i < matchPattern.size()-1; i++)
        {
            final ElementPattern pattern = matchPattern.get(i);

            if (pattern instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) pattern;
                // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
                traversal = filterByPattern(traversal, edgePattern);
                traversal = asInstance(traversal, edgePattern);
                traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
            }
            else if (pattern instanceof NodePattern)
            {
                traversal = filterByPattern(traversal, pattern);
                traversal = asInstance(traversal, pattern);
            }
            else 
            {
                System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
            }
        }
        
        if (tail instanceof EdgePattern)
        {
            EdgePattern tailEdgePattern = (EdgePattern) tail;
            traversal = traversal.toE(toGremlinDirection(tailEdgePattern.direction));
        }

        traversal = filterByPattern(traversal, tail);
        traversal = asInstance(traversal, tail);


        System.out.println("(1) match traversal: " + traversal.explain().prettyPrint());
        return traversal;
    }

    public GraphTraversal<Vertex, Vertex> vertexStart()
    {
        return start();
    }

    public GraphTraversal<Edge, Edge> edgeStart()
    {
        return start();
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


    public GraphTraversal<Vertex, Vertex> compileStartingElementPattern(
        GraphTraversal<Vertex, Vertex> traversal, ElementPattern pattern, HashSet<String> capturedSet)
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

    // outer captured set is variables from outside the paramaterised path
    // these variables need to be joined on (using where)
    // other variables encountered need to be captured in a group (usinig as)
    public GraphTraversal<Vertex, Vertex> compileRestrictedPatternContinued(GraphTraversal<Vertex, Vertex> traversal, List<PathComponent> components, Set<String> outerCapturedSet)
    {
        HashSet<String> capturedSet = new HashSet<>();
        HashSet<String> variables = pathPatternVariables(components);

        GraphTraversal<Vertex, ?> intermediary = null;

        for (PathComponent component : components)
        {
            if (component instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) component;
                // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                intermediary = traversal.toE(toGremlinDirection(edgePattern.direction));
                intermediary = compileElementPattern(intermediary, edgePattern, capturedSet, outerCapturedSet);
                traversal = intermediary.toV(toGremlinDirection(edgePattern.direction).opposite());
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

    public GraphTraversal<Vertex, Vertex> compileParenPathPattern(
        GraphTraversal<Vertex, Vertex> traversal, ParenPathPattern parenPathPattern, 
        Set<String> capturedVariables, Set<String> outerVariables)
    {
        List<PathComponent> parenComponents = parenPathPattern.pathPattern().pathSequence();
        
        // all variables in the outer path that are currently uncaptured- that come after the paren path
        HashSet<String> uncapturedOuterVariables = subtraction(outerVariables, capturedVariables);
        
        // variables shared by this paren path and the outer path that come after this paren path
        HashSet<String> uncapturedInnerVariables = null;
        // potentially expensive inner variable calculation, avoid if possible
        if (uncapturedOuterVariables.size() > 0)
        {
            HashSet<String> innerVariables = pathPatternVariablesAllLayers(parenComponents);
            uncapturedInnerVariables = intersection(uncapturedOuterVariables, innerVariables);
        }
        else 
        {
            uncapturedInnerVariables = new HashSet<>();
        }

        Quantifier quantifier = parenPathPattern.quantifier();
        boolean uncaptured = uncapturedInnerVariables.size() > 0; 
        boolean unroll =  quantifier.b() > 1; 

        if (uncaptured && unroll)
        {
            // if the iteration is degenerate (a = 1, b = 1), don't include captured post variables in outerSet    
            // just add them to the current captured set and continue after adding the subtraversal
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
        HashSet<String> variables = pathPatternVariables(components);

        String pathHashCode = Integer.toString(qualifiedPathPattern.hashCode());
        String startLabel = "#COMPILER_s%s".formatted(pathHashCode);
        String endLabel = "#COMPILER_e%s".formatted(pathHashCode);
        System.out.println(startLabel);
        

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
                temp = traversal.toE(toGremlinDirection(edgePattern.direction));
                temp = compileElementPattern(temp, edgePattern, capturedSet);
                traversal = temp.toV(toGremlinDirection(edgePattern.direction).opposite());
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

    public GraphTraversal<Vertex, Vertex> compileRestrictedPatterns(GraphTraversal<Vertex, Vertex> traversal, List<QualifiedPathPattern> qualifiedPathPatterns)
    {
        HashSet<String> capturedSet = new HashSet<>();

        for (QualifiedPathPattern qualifiedPathPattern : qualifiedPathPatterns)
        {
            traversal = compileRestrictedPattern(traversal, qualifiedPathPattern, capturedSet);
        }

        return traversal;
    }

    public Stream<String> pathPatternVariablesAllLayersStream(List<PathComponent> pathSequence)
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

    public HashSet<String> pathPatternVariablesAllLayers(List<PathComponent> pathSequence)
    {
        return new HashSet<>(pathPatternVariablesAllLayersStream(pathSequence).toList());
    }

    public HashSet<String> pathPatternVariables(List<PathComponent> pathSequence)
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

    public HashSet<String> pathPatternVariables(QualifiedPathPattern pathPattern)
    {
        return pathPatternVariables(pathPattern.pathPattern().pathSequence());
    }

    public <T> boolean intersects(Set<T> a, Set<T> b)
    {
        Set<T> c = a.size() < b.size() ? a : b;
        for (T e : c) 
        {
            if (b.contains(e))
            {
                return true;
            }
        }
        return false;
    }

    public <T> HashSet<T> intersection(Set<T> a, Set<T> b)
    {
        HashSet<T> c = new HashSet<T>(a);

        c.retainAll(b);
        return c;
    }

    public <T> HashSet<T> subtraction(Set<T> a, Set<T> b)
    {
        HashSet<T> c = new HashSet<T>(a);
        c.removeAll(b);

        return c;
    }

    public List<QualifiedPathPattern> orderRestrictedPathPatterns(List<QualifiedPathPattern> restrictedPathPatterns)
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
    
    public GraphTraversal<Vertex, ?> compileToTraversal(MatchExpression matchExpression)
    {        
        GraphTraversal<Vertex, Vertex> traversal = start();

        // verifies there are no variables in parenthesised subpaths that are used outside of their core path. 
        // MatchPatternFactory.verifyNesting(matchExpression.pathPatterns);

        Map<String, Boolean> intersectionMap = MatchPatternFactory.getIntersectionMap(matchExpression.pathPatterns);

        List<QualifiedPathPattern> restrictedPathPatterns = new ArrayList<>();
        List<PathPattern> unrestrictedPathPatterns = new ArrayList<>();

        for (QualifiedPathPattern pathPattern : matchExpression.pathPatterns)
        {
            if (pathPattern.variableName().isPresent() || pathPattern.evaluationMode() != EvaluationMode.WALK)
            {
                restrictedPathPatterns.add(pathPattern);
            }
            else 
            {
                // maximal match usage:
                // if the pattern contains iteration
                    // if the iteration(s) contain outer variable references
                        // if those iterations are small
                            // unroll those iterations, add to unrestricted path patterns
                        // otherwise
                            // add to restricted path patterns
                    // otherwise
                        // add to unrestricted path patterns
                // otherwise
                    // add to unrestricted path patterns

                // for now add everything to restricted
                restrictedPathPatterns.add(pathPattern);
            }
        }

        // ensures that connected path patterns are next to each other and ordered by some dfs of their graph
        // restrictedPathPatterns = orderRestrictedPathPatterns(restrictedPathPatterns);

        traversal = compileRestrictedPatterns(traversal, restrictedPathPatterns);
        
        List<PathPattern> matchPathPatterns = MatchPatternFactory.makeMatchPatterns(unrestrictedPathPatterns, intersectionMap);

        if (matchPathPatterns.size() == 0)
        {
            return traversal;   
        }
        // EnumMap<EvaluationMode, List<PathPattern>> pathPatterns = new EnumMap<>(EvaluationMode.class);
        // for (EvaluationMode mode : EvaluationMode.values()) 
        //     pathPatterns.put(mode, new ArrayList<>());

        // for (QualifiedPathPattern p : matchExpression.pathPatterns)
        // {
        //     final EvaluationMode mode = p.evaluationMode();
        //     final PathPattern pattern = p.pathPattern();
            
        //     pathPatterns.get(mode).add(pattern);
        // }

        // OrderedPathResult orderedPathResult = MatchPatternFactory.makeOrderedPaths(pathPatterns);
        
        // Map<EvaluationMode, List<OrderedPathPattern>> orderedPathPatterns = 
        //     orderedPathResult.orderedPaths();
        // Map<String, VariableOccurenceCounter> variableOccurences = orderedPathResult.variableOccurences();

        // Optional<String> jointVariable = MatchPatternFactory.getJointVariable(variableOccurences);

        // boolean restrictedRan = 
        //     orderedPathPatterns.get(EvaluationMode.SIMPLE).size() > 0 || 
        //     orderedPathPatterns.get(EvaluationMode.ACYCLIC).size() > 0 ||
        //     orderedPathPatterns.get(EvaluationMode.TRAIL).size() > 0;

        // orderedPathPatterns.get(EvaluationMode.SIMPLE);

        // // if there's a variable shared between the path-based and match-based
        // // patterns then select it before running match
        // // assumes our match-based patterns are all connected
        // if (jointVariable.isPresent())
        // {
        //     // we don't actually want to select to any join variables
        //     // leading as statements in match that correspond to old as variables 
        //     // will maintain identity (i.e. jump back)
        //     // as such the point the traverser enters the match statement isn't meaningful
        //     // unless there is an anonymous pattern starting a fragment(?)
        //     // traversal = traversal.select(jointVariable.get());
        // }
        // else if (restrictedRan)
        // {
        //     throw new SemanticErrorException("Match statement patterns must all be connected");
        // }

        // we currently distinguish match step patterns vs path patterns purely by EvalMode
        // in the future when path capture (p = ...) is supported will need to expand to include 
        // captured WALK patterns as path patterns, not match step patterns.
        // List<List<OrderedElementPattern>> matchPatterns = 
            // MatchPatternFactory.makeMatchPatterns(orderedPathPatterns.get(EvaluationModeCategory.unrestrictedMode()));

        System.out.println("Match Pattern Fragments: " + matchPathPatterns.toString());

        ArrayList<GraphTraversal<?,?>> matchTraversals = new ArrayList<>();
        
        for (PathPattern matchPathPattern : matchPathPatterns)
        {
            throw new SyntaxErrorException("Match pattern based execution currently unsupported");
            // matchTraversals.add(compileToMatchTraversal(matchPathPattern.pathSequence()));
        }


        GraphTraversal<?,?>[] traversalArray = matchTraversals.toArray(new GraphTraversal<?, ?>[0]);


        // we must make a new variable to take
        GraphTraversal<Vertex, Map<String, Object>> resultTraversal = traversal.match(traversalArray);

        if (matchExpression.whereClause.isPresent())
        {
            // add checking of where clause by compiling expression to predicate
            // traversal.where()
            System.out.println("Where clause unsupported");
        }


        return resultTraversal;
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
            return traversal.select(Pop.mixed, array[0]);
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
        // GraphTraversal<Vertex, Vertex> traversal = V();

        if (query.matchExpressions.size() > 1)
        {
            System.out.println("Multi match programs not supported");
            return null;
        }

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
            else {
                throw new SyntaxErrorException("Bad return syntax");
            }
        }


        GraphTraversal<Vertex, ?> traversal = 
            compileToTraversal(query.matchExpressions.get(0));

        if (query.returnStatement.setQuantifier() != SetQuantifier.ALL)
        {
            System.out.println("Distinct not currently supported");
        }

        // return everything matched
        if (query.returnStatement.returnItems().size() == 1 
            && query.returnStatement.returnItems().get(0) instanceof Asterisk)
        {
            throw new SyntaxErrorException("Asterisk return currently unsupported");
            // return traversal;
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
        if (program.queries.size() == 1)
        {
            return compileToTraversal(program.queries.get(0));
        }

        ArrayList<GraphTraversal<Vertex, Map<String,Object>>> queryTraversals = new ArrayList<>();
        for (GqlQuery query : program.queries) 
        {
            queryTraversals.add(compileToTraversal(query));
        }

        GraphTraversal<Vertex, Map<String,Object>> fullTraversal = queryTraversals.get(0);
        for (int i = 1; i < queryTraversals.size(); i++)
        {
            fullTraversal = conjoinTraversals(
                fullTraversal, 
                program.conjunctions.get(i-1), 
                queryTraversals.get(1));
        }

        return fullTraversal;
    }
    
}
