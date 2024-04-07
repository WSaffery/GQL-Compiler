package gql_gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import ast.GqlProgram;
import ast.expressions.Expression;
import ast.expressions.Value;
import ast.expressions.references.NameExpression;
import ast.expressions.references.PropertyReference;
import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.NodePattern;
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
import gql_gremlin.matching.MatchExpression;
import gql_gremlin.matching.MatchPattern;
import gql_gremlin.matching.MatchPatternFactory;
import gql_gremlin.matching.OrderedElementPattern;
import gql_gremlin.matching.OrderedPathPattern;

// import com.tinkerpop.blueprints.Direction;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static gql_gremlin.helpers.GremlinHelpers.*;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


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

record MatchPropertyResults (
    List<PropertyResult> headPropertyResult,
    List<PropertyResult> endPropertyResult
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

    // public GraphTraversal<Edge, Edge> startTraversal(EdgePattern edgePattern)
    // {
    //     GraphTraversal<Edge, Edge> startTraversal = start();

    //     final LabelExpression labelExpression = edgePattern.labelExpression();
    //     final String variableName = edgePattern.variableName;

    //     if (labelExpression != null) 
    //     {         
    //         if (labelExpression instanceof Label)
    //         {
    //             Label label = (Label) labelExpression;
    //             startTraversal = startTraversal.hasLabel(label.getValue());
    //         }
    //         else if (labelExpression instanceof WildcardLabel)
    //         {
    //             System.out.println("Too many labels!");
    //         }
    //         else 
    //         {
    //             throw new SemanticErrorException("Only basic label expressions are implemented");
    //         }
    //     }
        

    //     if (variableName != null)
    //     {
    //         startTraversal = startTraversal.as(variableName);
    //     }
        
    //     return startTraversal;
    // }

    // public static <A> GraphTraversal<A, A> startTraversal() 
    // {
    //     GraphTraversal<A, A> startTraversal = start();

    //     return startTraversal;
    // }

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

    public GraphTraversal<?, ?> filterByPattern(GraphTraversal<?, ?> traversal, ElementPattern pattern)
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
                System.out.println("Too many labels!");
            }
            else 
            {
                throw new SemanticErrorException("Only basic label expressions are implemented");
            }
        }

        for (Entry<String, Value> item : pattern.properties().entrySet())
        {
            String key = item.getKey();
            Object value = item.getValue();
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
    public GraphTraversal<?, ?> asInstance(OrderedElementPattern pattern)
    {
        if (pattern.variableName().isPresent())
        {
            if (pattern.preceded()) 
            {
                return where(P.eq(pattern.variableName().get()));
            }
            else 
            {
                return as(pattern.variableName().get());
            }
        }
        return identity();
    }

    // if the pattern's variable has been preceed, ensure we match the preceeded captured elements
    // otherwise, do nothing
    public GraphTraversal<?, ?> filterByPrior(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
    {
        if (pattern.variableName().isPresent() && pattern.preceded())
        {
            return traversal.where(P.eq(pattern.variableName().get()));
        }
        return traversal;
    }

    // if the pattern's variable hasn't been preceeded, capture the current elements as that variable
    // otherwise, do nothing
    public GraphTraversal<?, ?> captureInstance(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
    {
        if (pattern.variableName().isPresent() && !pattern.preceded())
        {
            return traversal.as(pattern.variableName().get());
        }
        return traversal;
    }


    public GraphTraversal<?, ?> compileToTraversal(MatchPattern matchPattern)
    {
        GraphTraversal<?, ?> traversal = null;

        OrderedElementPattern head = matchPattern.headPattern();
        OrderedElementPattern tail = matchPattern.tailPattern();

        traversal = asInstance(head);

        traversal = filterByPattern(traversal, head.pattern());
        if (head.pattern() instanceof EdgePattern)
        {
            EdgePattern headEdgePattern = (EdgePattern) head.pattern();
            traversal = traversal.toV(toGremlinDirection(headEdgePattern.direction).opposite());
        }
        else
        {
            assert(head.pattern() instanceof NodePattern);
        }

        for (OrderedElementPattern orderedPattern : matchPattern.middlePatterns())
        {
            // assert(pattern.variableName == null);
            final ElementPattern pattern = orderedPattern.pattern();
            if (pattern instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) pattern;
                // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
                traversal = filterByPrior(traversal, orderedPattern);
                traversal = filterByPattern(traversal, edgePattern);
                traversal = captureInstance(traversal, orderedPattern);
                traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
            }
            else if (pattern instanceof NodePattern)
            {
                NodePattern nodePattern = (NodePattern) pattern;
                traversal = filterByPrior(traversal, orderedPattern);
                traversal = filterByPattern(traversal, nodePattern);
                traversal = captureInstance(traversal, orderedPattern);
            }
            else 
            {
                System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
            }
        }

        traversal = filterByPrior(traversal, tail); // filter if preceded
        traversal = filterByPattern(traversal, tail.pattern());
        traversal = captureInstance(traversal, tail); // capture if not preceded

        
        if (tail.pattern() instanceof EdgePattern)
        {
            EdgePattern tailEdgePattern = (EdgePattern) tail.pattern();
            traversal = traversal.toE(toGremlinDirection(tailEdgePattern.direction));
        }
        else
        {
            assert(tail.pattern() instanceof NodePattern);
        }


        System.out.println("match traversal: " + traversal.explain().prettyPrint());
        return traversal;
    }

    
    public GraphTraversal<?, ?> compileToMatchTraversal(List<OrderedElementPattern> matchPattern)
    {
        OrderedElementPattern head = matchPattern.get(0);
        OrderedElementPattern tail = matchPattern.get(matchPattern.size()-1);

        // Although we can determine the eventual type parameters <S,E> of our traversal 
        // from head and tail, we can only do this at runtime. Furthermore graph traversal's
        // type parameter mutates over the course of building the traversal and starts 
        // inevitably as <S,S> rather than <S,E>.
        // As such we must take parameters ?,? to make the variable valid throughout the process
        // of building the final traversal. Even if we could somehow set the parameters at runtime.
        
        GraphTraversal<?, ?> traversal = null;
        traversal = asInstance(head);

        traversal = filterByPattern(traversal, head.pattern());
        if (head.pattern() instanceof EdgePattern)
        {
            EdgePattern headEdgePattern = (EdgePattern) head.pattern();
            traversal = traversal.toV(toGremlinDirection(headEdgePattern.direction).opposite());
        }
        else
        {
            assert(head.pattern() instanceof NodePattern);
        }

        for (int i = 0; i < matchPattern.size(); i++)
        {
            final boolean end = i == matchPattern.size() - 1;
            final OrderedElementPattern orderedPattern = matchPattern.get(i);
            final ElementPattern pattern = orderedPattern.pattern();

            if (pattern instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) pattern;
                // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
                traversal = filterByPrior(traversal, orderedPattern);
                traversal = filterByPattern(traversal, edgePattern);
                traversal = captureInstance(traversal, orderedPattern);
                if (!end)
                {
                    traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
                }
            }
            else if (pattern instanceof NodePattern)
            {
                NodePattern nodePattern = (NodePattern) pattern;
                traversal = filterByPrior(traversal, orderedPattern);
                traversal = filterByPattern(traversal, nodePattern);
                traversal = captureInstance(traversal, orderedPattern);
            }
            else 
            {
                System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
            }
        }
        
        if (tail.pattern() instanceof EdgePattern)
        {
            EdgePattern tailEdgePattern = (EdgePattern) tail.pattern();
            traversal = traversal.toE(toGremlinDirection(tailEdgePattern.direction));
        }
        else
        {
            assert(tail.pattern() instanceof NodePattern);
        }

        traversal = filterByPrior(traversal, tail); // filter if preceded
        traversal = filterByPattern(traversal, tail.pattern());
        traversal = captureInstance(traversal, tail); // capture if not preceded


        System.out.println("match traversal: " + traversal.explain().prettyPrint());
        return traversal;
    }


    public GraphTraversal<?, ?> startMatch(QualifiedPathPattern pathPattern)
    {
        ElementPattern pattern1 = pathPattern.pathPattern().pathSequence().get(0);
        if (pattern1 instanceof NodePattern)
        {
            return V();
        }
        else 
        {
            GraphTraversal<Edge, Edge> startTraversal = start();
            return startTraversal;
        }
    }

    public GraphTraversal<Vertex, Vertex> vertexStart()
    {
        return start();
    }

    public GraphTraversal<Edge, Edge> edgeStart()
    {
        return start();
    }

    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(MatchExpression matchExpression)
    {        
        GraphTraversal<Vertex, ?> traversal = start();

        EnumMap<EvaluationMode, List<PathPattern>> pathPatterns = new EnumMap<>(EvaluationMode.class);
        pathPatterns.replaceAll((k, v) -> new ArrayList<>());

        for (QualifiedPathPattern p : matchExpression.pathPatterns)
        {
            final EvaluationMode mode = p.evaluationMode();
            final PathPattern pattern = p.pathPattern();
            
            pathPatterns.get(mode).add(pattern);
        }

        EnumMap<EvaluationMode, List<OrderedPathPattern>> orderedPathPatterns = 
            MatchPatternFactory.makeOrderedPaths(pathPatterns);

        for (EvaluationMode mode : EvaluationModeCategory.restrictedModes())
        {
            // 
        }


        // we currently distinguish match step patterns vs path patterns purely by EvalMode
        // in the future when path capture (p = ...) is supported will need to expand to include 
        // captured WALK patterns as path patterns, not match step patterns.
        List<List<OrderedElementPattern>> matchPatterns = 
            MatchPatternFactory.makeMatchPatterns(orderedPathPatterns.get(EvaluationModeCategory.unrestrictedMode()));

        ArrayList<GraphTraversal<?,?>> matchTraversals = new ArrayList<>();
        
        for (List<OrderedElementPattern> matchPattern : matchPatterns)
        {
            matchTraversals.add(compileToMatchTraversal(matchPattern));
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


    public static GraphTraversal<Vertex, Map<String,Object>> variadicSelect(GraphTraversal<Vertex, ?> traversal, AbstractCollection<String> selectKeys)
    {
        if (selectKeys.size() == 0) {
            return null;
        }

        String[] array = selectKeys.toArray(new String[0]);

        if (array.length == 1)
        {
            return traversal.select(array[0]);
        }
        else if (array.length == 2)
        {
            return traversal.select(array[0], array[1]);
        }
        else 
        {
            // avoids using copyOfRange explicitly, does so implicitly
            String[] subarray = Arrays.asList(array).subList(2, array.length).toArray(new String[0]);
            return traversal.select(array[0], array[1], subarray);
        }
    }

    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(GqlQuery query)
    {
        // GraphTraversal<Vertex, Vertex> traversal = V();

        if (query.matchExpressions.size() > 1)
        {
            System.out.println("Multi match programs not supported");
            return null;
        }

        ArrayList<String> fullReturnNames = new ArrayList<>();
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
                }
                else if (expr instanceof PropertyReference)
                {
                    PropertyReference propertyReference = (PropertyReference) expr;
                    String referenceName = propertyReference.name();
                    String referenceKey = propertyReference.key();
                    
                    returnReferences.putIfAbsent(referenceName, new ArrayList<>());
                    returnReferences.get(referenceName).add(new PropertyResult(referenceKey, alias));

                    returnNames.add(alias);
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


        GraphTraversal<Vertex, Map<String,Object>> traversal = 
            compileToTraversal(query.matchExpressions.get(0));

        if (query.returnStatement.setQuantifier() != SetQuantifier.ALL)
        {
            System.out.println("Distinct not currently supported");
        }

        // return everything matched
        if (query.returnStatement.returnItems().size() == 1 
            && query.returnStatement.returnItems().get(0) instanceof Asterisk)
        {
            return traversal;
        }

        traversal = variadicSelect(traversal, returnNames);

        for (@SuppressWarnings("unused") String returnName : returnNames)
        {
            // by default we just map out all the properties of every returned variable
            traversal = traversal.by(valueMap());
        }
        
        return traversal;
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
