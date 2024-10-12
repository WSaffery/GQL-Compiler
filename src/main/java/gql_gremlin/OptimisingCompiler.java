package gql_gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.javatuples.Triplet;

import ast.GqlProgram;
import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.PathComponent;
import ast.patterns.PathPattern;
import ast.patterns.QualifiedPathPattern;
import ast.queries.GqlQuery;
import data.Summary;
import gql_gremlin.matching.MatchExpression;
import gql_gremlin.query.QueryEdge;
import gql_gremlin.query.QueryGraph;
import gql_gremlin.query.QueryNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


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

// Manipulates the order of paths given in the given GqlProgram in compileToTraversal
// to optimise intermediate result size, and then compiles using the 
// 'backend' compiler, which doesn't perform (significant) order changes.
public class OptimisingCompiler implements Compiler {
    Compiler backend;
    Summary summary;

    public OptimisingCompiler(Compiler compiler, Summary summary) 
    {
        this.backend = compiler;
        this.summary = summary;
    }

    public OptimisingCompiler(Summary summary)
    {
        this(new GremlinCompiler(), summary);
    }

    public List<Triplet<QueryNode, QueryEdge, QueryNode>> findOrder(QueryGraph graph, Summary summary)
    {
        List<List<Triplet<QueryNode, QueryEdge, QueryNode>>> orders = new ArrayList<>();
        List<Double> costs = new ArrayList<>();

        // System.out.println("Edges: " + graph.edges().stream().map(edge -> edge.labels()).toList());

        // run basic greedy algo for approximate best order from each possible start node (minsel)
        for (QueryNode startNode : graph.nodes())
        {
            List<Triplet<QueryNode, QueryEdge, QueryNode>> order = new ArrayList<>();
            HashSet<QueryEdge> pendingEdges = new HashSet<>(graph.edges());
            HashSet<QueryNode> capturedNodes = new HashSet<>(Set.of(startNode));
            Double cost = 0d;

            // System.out.println("---\nStart Node: " + startNode);
            while (pendingEdges.size() > 0)
            {
                // System.out.println("Pending Edges: " + pendingEdges.stream().map(edge -> edge.labels()).toList());
                // System.out.println("Order: " + order);

                Double bestCost = null;
                QueryEdge bestEdge = null;
                QueryNode bestHead = null;
                QueryNode bestTail = null;
                
                for (QueryEdge edge : pendingEdges)
                {
                    QueryNode nodeA = edge.nodes().getValue0();
                    QueryNode nodeB = edge.nodes().getValue1();

                    if (!capturedNodes.contains(nodeA) && !capturedNodes.contains(nodeB))
                    {
                        continue;
                    }

                    // costs of joining by edge nextEdge from nodeA and nodeB respectively
                    // since nodes and edges might allow multiple labels, we calculate this externally
                    Double costA = summary.getAvgCardinality(nodeA, edge, nodeB);
                    Double costB = summary.getAvgCardinality(nodeB, edge, nodeA);
                    
                    Double currentCost;
                    QueryNode currentHead;
                    QueryNode currentTail;
                    QueryEdge currentEdge;
                    if (capturedNodes.contains(nodeA) && capturedNodes.contains(nodeB))
                    {
                        if (costB > costA) 
                        {
                            currentHead = nodeB;
                            currentTail = nodeA;
                            currentEdge = edge.reversed();
                            currentCost = costB;
                        }
                        else 
                        {
                            currentHead = nodeA;
                            currentTail = nodeB;
                            currentEdge = edge;
                            currentCost = costA;
                        }
                    }
                    else if (capturedNodes.contains(nodeA)) 
                    {
                        currentHead = nodeA;
                        currentTail = nodeB;
                        currentEdge = edge;
                        currentCost = costA;
                    }
                    else 
                    {
                        currentHead = nodeB;
                        currentTail = nodeA;
                        currentEdge = edge.reversed();
                        currentCost = costB;
                    }

                    if (bestCost == null || currentCost < bestCost)
                    {
                        bestEdge = currentEdge;
                        bestHead = currentHead;
                        bestTail = currentTail;
                        bestCost = currentCost;
                    }
                }

                if (bestCost == null || bestEdge == null || bestHead == null || bestTail == null)
                {
                    throw new RuntimeException("Logic Error in Optimising Compiler, Best Cost or Edge or Head or Tail = Null");
                }

                pendingEdges.remove(bestEdge.origin());
                capturedNodes.add(bestTail);
                cost += bestCost;
                order.add(Triplet.with(bestHead, bestEdge, bestTail));
            }

            costs.add(cost);
            orders.add(order);
        }

        int min = 0;
        for (int i = 1; i < costs.size(); i++)
        {
            if (costs.get(i) < costs.get(min)) 
            {
                min = i;
            }
        }
        
        return orders.get(min);
    }

    public List<PathPattern> makePaths(List<Triplet<QueryNode, QueryEdge, QueryNode>> ordering)
    {
        List<PathPattern> paths = new ArrayList<>();
        ArrayList<PathComponent> currentComponents = new ArrayList<>();
        QueryNode prior = null;
        
        for (Triplet<QueryNode, QueryEdge, QueryNode> join : ordering)
        {
            QueryNode nodeA = join.getValue0();
            QueryEdge edge = join.getValue1();
            QueryNode nodeB = join.getValue2();

            if (prior == null)
            {
                currentComponents.add(nodeA.pattern());   
                currentComponents.add(edge.pattern());
                currentComponents.add(nodeB.pattern());
            }
            else if (prior.variableName().equals(nodeA.variableName()))
            {
                currentComponents.add(edge.pattern());
                currentComponents.add(nodeB.pattern());
            }
            else 
            {
                paths.add(new PathPattern(currentComponents));
                currentComponents = new ArrayList<>();
                currentComponents.add(nodeA.pattern());
                currentComponents.add(edge.pattern());
                currentComponents.add(nodeB.pattern());
            }

            prior = nodeB;
        }

        if (currentComponents.size() > 0)
        {
            paths.add(new PathPattern(currentComponents));
        }

        return paths;
    }

    public void pruneCaptures(List<PathPattern> pathPatterns, HashSet<String> externalRequiredVars)
    {
        HashSet<String> referencedVariables = new HashSet<>();
        HashSet<String> joinVariables = new HashSet<>();
        for (PathPattern pathPattern : pathPatterns)
        {
            for (Optional<String> possibleVariable : pathPattern.variableSequence())
            {
                if (possibleVariable.isEmpty()) continue;
                String variable = possibleVariable.get();
                if (referencedVariables.contains(variable))
                {
                    joinVariables.add(variable);
                }
                referencedVariables.add(variable);
            }
        }

        HashSet<String> requiredVariables = new HashSet<>(joinVariables);
        requiredVariables.addAll(externalRequiredVars);

        // strip unneeded variables names
        for (PathPattern pathPattern : pathPatterns)
        {
            for (PathComponent component : pathPattern.pathSequence())
            {
                if (component instanceof ElementPattern)
                {
                    ElementPattern pattern = (ElementPattern) component;
                    if (pattern.variableName.isPresent())
                    {
                        String variable = pattern.variableName.get();
                        if (!requiredVariables.contains(variable))
                        {
                            pattern.variableName = Optional.empty();
                        }
                    }
                }
            }
        }

    }

    // optimises join order of given query (in place)
    public void optimiseQuery(GqlQuery query)
    {
        boolean returnAll = query.returnStatement.returnAll();
        boolean returnNone = query.returnStatement.returnNone();

        // we require that no match expression is superfluous
        // that is, there is only one match statement for the triplet (graph g, mandatory value m, where clause w)
        // this is the case in our test set
        // TODO!: check this in our parser, or do a transform

        // under these conditions there is generally only one match statement
        // with this in mind we optimise the first match expression only
        
        MatchExpression matchExpression = query.matchExpressions.get(0);

        List<PathPattern> optimisablePathPatterns = new ArrayList<>();
        ListIterator<QualifiedPathPattern> iter = matchExpression.pathPatterns.listIterator();
        while (iter.hasNext())
        {
            QualifiedPathPattern pattern = iter.next();
            if (pattern.ordinary())
            {
                optimisablePathPatterns.add(pattern.pathPattern());
                iter.remove();
            }
        }
        // add all "ordinary" path patterns to our optimisable list, then remove them from the match statement

        QueryGraph graph = QueryGraph.constructQueryGraph(optimisablePathPatterns);

        Summary summary = Summary.getLsqb01Summary();
        List<Triplet<QueryNode, QueryEdge, QueryNode>> order = findOrder(graph, summary);
        
        List<PathPattern> newPathPatterns = makePaths(order);
        if (!returnAll)
        {
            HashSet<String> requiredVars = new HashSet<>();
            if (!returnNone)
            {
                requiredVars.addAll(query.returnStatement.variableNames());
            }
            if (matchExpression.whereClause.isPresent())
            {
                requiredVars.addAll(matchExpression.whereClause.get().referencedVariables());
            }
            
            pruneCaptures(newPathPatterns, requiredVars);
        }

        // newPathPatterns.iterator().forEachRemaining(p -> matchExpression.pathPatterns.add(QualifiedPathPattern.makeOrdinary((p))));
        matchExpression.pathPatterns.addAll(newPathPatterns.stream().map(p -> QualifiedPathPattern.makeOrdinary(p)).toList());
    }

    // discards graphName information from focused match clauses
    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(GqlProgram program)
    {
        for (GqlQuery query : program.body.queries())
        {
            optimiseQuery(query);    
        }

        return backend.compileToTraversal(program);
    }
    
}
