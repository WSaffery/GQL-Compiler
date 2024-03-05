package gql_gremlin;

import gql.enums.EvaluationMode;
import gql.enums.QueryConjunctor;
import gql.expressions.Expression;
import gql.expressions.references.NameExpression;
import gql.expressions.references.PropertyReference;
import gql.expressions.values.FloatingPointNumber;
import gql.expressions.values.GqlIdentifier;
import gql.expressions.values.GqlString;
import gql.expressions.values.Label;
import gql.expressions.values.TruthValue;
import gql.expressions.values.Value;
import gql.patterns.EdgePattern;
import gql.patterns.ElementPattern;
import gql.patterns.NodePattern;
import gql.patterns.QualifiedPathPattern;
import gql.returns.ReturnExpression;
import gql.returns.ReturnItem;
import gql.returns.ReturnStatement;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

// import com.tinkerpop.blueprints.Direction;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static gql_gremlin.helpers.GremlinHelpers.*;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


enum GQLDirection {
    UNDIRECTED,
    RIGHT_TO_LEFT,
    LEFT_TO_RIGHT
}

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

    public GraphTraversal<Edge, Edge> startTraversal(EdgePattern edgePattern)
    {
        GraphTraversal<Edge, Edge> startTraversal = start();

        // list of label lists
        // we match any object that has all the labels in any one of our label lists
        // for now, simply assert there's only one label list
        if (edgePattern.labels != null) 
        {

            String[] labelStrings = getLabelStrings(edgePattern.labels);
            if (labelStrings.length == 1)
            {
                startTraversal = startTraversal.hasLabel(labelStrings[0]);
            }
            else if (labelStrings.length > 1)
            {
                System.out.println("Too many labels!");
            }
        }
        

        if (edgePattern.variableName != null)
        {
            startTraversal = startTraversal.as(edgePattern.variableName.getId());
        }
        
        return startTraversal;
    }

    public static <A> GraphTraversal<A, A> startTraversal() 
    {
        GraphTraversal<A, A> startTraversal = start();

        return startTraversal;
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

    public GraphTraversal<?, ?> filterByPattern(GraphTraversal<?, ?> traversal, ElementPattern pattern)
    {
        String[] labels = getLabelStrings(pattern.labels);
        if (labels.length == 1)
        {
            traversal = traversal.hasLabel(labels[0]);
        }
        else if (labels.length > 1)
        {
            System.out.println("Too many labels in edge pattern");
        }

        for (Entry<GqlIdentifier, Value> item : pattern.properties.entrySet())
        {
            Value value = item.getValue();
            if (value instanceof FloatingPointNumber)
            {
                FloatingPointNumber num = (FloatingPointNumber) value;
                traversal = traversal.has(item.getKey().getId(), num.toValue());
            }
            else if (value instanceof GqlString)
            {
                GqlString str = (GqlString) value;
                traversal = traversal.has(item.getKey().getId(), str.toString());
            }
            else if (value instanceof TruthValue)
            {
                TruthValue bool = (TruthValue) value;
                traversal = traversal.has(item.getKey().getId(), bool.isTrue());
            }
            else 
            {
                System.out.println("Property Value of type " + value.getClass().getName() + " is not supported");
            }
            
        }

        return traversal;
    }

    // TODO:
    // Match doesn't work over arbitrary traversals with arbitrary labels
    // Only works with a start label and an end label
    // need to split up paths into start end pairs
    public GraphTraversal<?, ?> compileToTraversal(QualifiedPathPattern pathPattern)
    {
        GraphTraversal<?, ?> traversal = null;
        // Store Traversal sidedness in enum
        // Set initial traversal including start using first element of path sequence
        int i = 0;
        final int end = pathPattern.pathPattern().getPathSequence().size();
        for (ElementPattern pattern : pathPattern.pathPattern().getPathSequence())
        {
            if (pattern instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) pattern;
                if (traversal == null) 
                {
                    String startLabel = edgePattern.variableName == null ? 
                        "START!" + pathPattern.hashCode() : 
                        edgePattern.variableName.getId(); 
                
                    traversal = as(startLabel);

                    traversal = filterByPattern(traversal, edgePattern);
                    traversal = traversal.toV(toGremlinDirection(edgePattern.direction));
                }
                else {
                    // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                    traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
                    traversal = filterByPattern(traversal, edgePattern);

                    if (edgePattern.variableName != null)
                    {
                        traversal = traversal.as(edgePattern.variableName.getId());
                    }

                    traversal = traversal.toV(toGremlinDirection(edgePattern.direction));
                    
                }
            }
            else if (pattern instanceof NodePattern)
            {
                NodePattern nodePattern = (NodePattern) pattern;
                if (traversal == null)
                {
                    String startLabel = nodePattern.variableName == null ? 
                        "START!" + pathPattern.hashCode() : 
                        nodePattern.variableName.getId(); 
                
                    traversal = as(startLabel);

                    // traversal = GremlinCompiler.<Vertex>startTraversal();
                    traversal = filterByPattern(traversal, nodePattern);
                }
                else 
                {
                    traversal = filterByPattern(traversal, nodePattern);
                    if (nodePattern.variableName != null && i == end-1)
                    {
                        traversal = traversal.as(nodePattern.variableName.getId());
                    }
                }
            }
            else 
            {
                System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
            }
            i += 1;
        }

        return traversal;
    }

    public GraphTraversal<?, ?> compileToTraversal(MatchPattern matchPattern)
    {
        GraphTraversal<?, ?> traversal = null;

        KeyPattern head = matchPattern.headPattern();
        KeyPattern tail = matchPattern.tailPattern();


        if (head.label() != null)
        {
            traversal = as(head.label());
        }

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

        for (ElementPattern pattern : matchPattern.middlePatterns())
        {
            assert(pattern.variableName == null);
            if (pattern instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) pattern;
                // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
                traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
                traversal = filterByPattern(traversal, edgePattern);
                traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
            }
            else if (pattern instanceof NodePattern)
            {
                NodePattern nodePattern = (NodePattern) pattern;
                traversal = filterByPattern(traversal, nodePattern);
            }
            else 
            {
                System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
            }
        }

        if (tail.label() != null)
        {
            traversal = traversal.as(tail.label());
        }

        traversal = filterByPattern(traversal, tail.pattern());
        if (head.pattern() instanceof EdgePattern)
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

    public GraphTraversal<?, ?> startMatch(QualifiedPathPattern pathPattern)
    {
        ElementPattern pattern1 = pathPattern.pathPattern().getPathSequence().get(0);
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

    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(MatchExpression matchExpression, ReturnStatement returnStatement)
    {
        ArrayList<GraphTraversal<?,?>> traversals = new ArrayList<>();
        
        ArrayList<MatchPattern> matchPatterns = new ArrayList<>();


        for (QualifiedPathPattern p : matchExpression.pathPatterns)
        {
            if (p.evaluationMode() == EvaluationMode.WALK)
            {
                matchPatterns.addAll(MatchPatternFactory.makeMatchPatterns(p.pathPattern()));
            }
            else 
            {
                System.out.printf("Evaluation mode %s not supported%n", p.evaluationMode().toString());
            }
        }

        for (MatchPattern m : matchPatterns)
        {
            traversals.add(compileToTraversal(m));
        }

        GraphTraversal<?,?>[] traversalArray = traversals.toArray(new GraphTraversal<?, ?>[0]);
        
        GraphTraversal<Vertex, Map<String,Object>> traversal = vertexStart().V().match(traversalArray);

        if (matchExpression.whereClause.isPresent())
        {
            // add checking of where clause by compiling expression to predicate
            // traversal.where()
            System.out.println("Where clause unsupported");
        }


        return traversal;
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


        GraphTraversal<Vertex,?> traversal = compileToTraversal(query.matchExpressions.get(0), query.returnStatement);

        ArrayList<String> returnNames = new ArrayList<>();
        ArrayList<Entry<String,String>> returnReferences = new ArrayList<>();

        for (ReturnItem item : query.returnStatement.getReturnItems())
        {
            if (item instanceof ReturnExpression)
            {
                Expression expr = ((ReturnExpression) item).getExpression();
                if (expr instanceof NameExpression) 
                {
                    NameExpression nameExpr = (NameExpression) expr;
                    returnNames.add(nameExpr.getName().getId());
                }
                else if (expr instanceof PropertyReference)
                {
                    PropertyReference propertyReference = (PropertyReference) expr;
                    String referenceName = propertyReference.name.getName().getId();
                    String referenceKey = propertyReference.key.getId();
                    returnReferences.add(Map.entry(
                        referenceName,
                        referenceKey));

                    returnNames.add(referenceName);
                    // should indicate this is only required for the reference
                    // and not be included in actual output
                }
                else 
                {
                    System.out.println("Unsupported return item");
                }
            }
        }

        HashSet<String> selectedNames = new HashSet<>(returnNames);
        for (Entry<String, String> e : returnReferences)
        {
            selectedNames.add(e.getKey());
        }

        // GraphTraversal<?,?>[] traversalArray = traversals.toArray(new GraphTraversal<?, ?>[0]);
        // traversal = union(traversalArray);

        // GraphTraversal<Vertex, Map<String,Object>> intermediary = variadicSelect(traversal, selectedNames);

        // for (Entry<String, String> e : returnReferences)
        // {
        //     intermediary = intermediary.from(e.getKey()).values(e.getValue());
        // }


        GraphTraversal<Vertex, Map<String,Object>> result = variadicSelect(traversal, returnNames);
        
        return result;
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