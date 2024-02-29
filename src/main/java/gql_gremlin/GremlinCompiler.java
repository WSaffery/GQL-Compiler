package gql_gremlin;

import antlr.GqlLexer;
import gql.enums.QueryConjunctor;
import gql.expressions.Expression;
import gql.expressions.references.NameExpression;
import gql.expressions.values.Label;
import gql.patterns.EdgePattern;
import gql.patterns.ElementPattern;
import gql.patterns.QualifiedPathPattern;
import gql.returns.ReturnExpression;
import gql.returns.ReturnItem;
import gql.returns.ReturnStatement;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;


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

    public GraphTraversal<?, ?> compileToTraversal(QualifiedPathPattern pathPattern)
    {
        GraphTraversal<?, ?> traversal = null;
        LastTraversed lastTraversed = null;
        // Store Traversal sidedness in enum
        // Set initial traversal including start using first element of path sequence
        for (ElementPattern pattern : pathPattern.pathPattern().getPathSequence())
        {
            if (pattern instanceof EdgePattern) 
            {
                EdgePattern edgePattern = (EdgePattern) pattern;
                if (lastTraversed == null) 
                {
                    GraphTraversal<Edge, Edge> startTraversal = start();
                    traversal = startTraversal;
                    lastTraversed = LastTraversed.E;

                    // list of label lists
                    // we match any object that has all the labels in any one of our label lists
                    // for now, simply assert there's only one label list
                    if (edgePattern.labels != null) 
                    {
                        if (edgePattern.labels.size() == 1)
                        {
                            ArrayList<Label> labels = edgePattern.labels.get(0);
                            if (labels.size() == 1) {
                                traversal.hasLabel(labels.get(0).toString());
                            }
                            else 
                            {
                                String labelString1 = labels.get(0).toString();
                                ArrayList<String> labelStrings = new ArrayList<>();
                                for (Label l : labels.subList(1, labels.size()))
                                {
                                    labelStrings.add(l.toString());
                                }
                                traversal.hasLabel(labelString1, (String[]) labelStrings.toArray());
                            }
                        }
                        else 
                        {
                            System.out.println("Multi label set patterns not supported");
                        }
                    }

                    if (edgePattern.properties != null) 
                    {
                        System.out.println("Property patterns not supported");
                    }
                    

                    if (edgePattern.variableName != null)
                    {
                        traversal.as(edgePattern.variableName.getId());
                    }
                }


            }
        }

        return traversal;
    }


    public GraphTraversal<?, ?> compileToTraversal(MatchExpression matchExpression, ReturnStatement returnStatement)
    {
        GraphTraversal<Vertex, Vertex> traversal = V();
        ArrayList<GraphTraversal<?,?>> traversals = new ArrayList<>();

        for (QualifiedPathPattern p : matchExpression.pathPatterns)
        {
            traversals.add(compileToTraversal(p));
        }

        GraphTraversal<?,?>[] traversalArray = (GraphTraversal<?, ?>[]) traversals.toArray();
        traversal.match(traversalArray);

        if (matchExpression.whereClause.isPresent())
        {
            // add checking of where clause by compiling expression to predicate
            // traversal.where()
            System.out.println("Where clause unsupported");
        }

        return traversal;
    }

    public static void variadicSelect(GraphTraversal<?, ?> traversal, ArrayList<String> selectKeys)
    {
        if (selectKeys.size() == 0) {
            return;
        }
        else if (selectKeys.size() == 1)
        {
            traversal.select(selectKeys.get(0));
        }
        else if (selectKeys.size() == 2) 
        {
            traversal.select(selectKeys.get(0), selectKeys.get(1));
        }
        else {
            String[] subarray = (String[]) selectKeys.subList(2, selectKeys.size()).toArray();
            traversal.select(selectKeys.get(0), selectKeys.get(1), subarray);
        }
    }

    public GraphTraversal<Vertex, Vertex> compileToTraversal(GqlQuery query)
    {
        GraphTraversal<Vertex, Vertex> traversal = V();
        ArrayList<GraphTraversal<?,?>> traversals = new ArrayList<>();

        for (MatchExpression m : query.matchExpressions)
        {
            traversals.add(compileToTraversal(m, query.returnStatement));
        }

        ArrayList<String> returnNames = new ArrayList<>();
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
                else 
                {
                    System.out.println("Unsupported return item");
                }
            }
        }

        GraphTraversal<?,?>[] traversalArray = (GraphTraversal<?, ?>[]) traversals.toArray();
        traversal.match(traversalArray);

        variadicSelect(traversal, returnNames);

        return traversal;
    }

    // discards graphName information from focused match clauses
    public GraphTraversal<Vertex, Vertex> compileToTraversal(GqlProgram program)
    {
        if (program.queries.size() == 1)
        {
            return compileToTraversal(program.queries.get(0));
        }

        ArrayList<GraphTraversal<Vertex, Vertex>> queryTraversals = new ArrayList<>();
        for (GqlQuery query : program.queries) 
        {
            queryTraversals.add(compileToTraversal(query));
        }

        GraphTraversal<Vertex, Vertex> fullTraversal = queryTraversals.get(0);
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
