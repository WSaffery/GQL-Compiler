package gql_gremlin.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;

import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.NodePattern;
import ast.patterns.PathComponent;
import ast.patterns.PathPattern;
import exceptions.UnsupportedFeatureException;

public class QueryGraph {
    public HashMap<String, QueryNode> graphMap; // contains all nodes
    public HashMap<String, QueryEdge> graphEdgeMap; // contains named edges
    ArrayList<QueryEdge> edges; // contains all edges

    private int anonCount;

    private QueryGraph()
    {
        graphMap = new HashMap<>();
        edges = new ArrayList<>();
        anonCount = 0;
    }

    public Set<String> nodeNames()
    {
        return graphMap.keySet();
    }

    public Collection<QueryNode> nodes()
    {
        return graphMap.values();
    }

    public ArrayList<QueryEdge> edges()
    {
        return edges;
    }

    // attempts to add a query node corresponding to the given node pattern to the graph,
    // and then return it.
    // if there is already a node with a matching variable name in the graph, 
    // return it instead
    private QueryNode addQueryNode(NodePattern nodePattern)
    {
        QueryNode result;
        String variableName;
        if (nodePattern.variableName().isPresent())
        {
            variableName = nodePattern.variableName().get();
            if (!graphMap.containsKey(variableName))
            {
                result = new QueryNode(nodePattern, variableName, new ArrayList<>());
                graphMap.put(variableName, result);
            }
            else 
            {
                result = graphMap.get(variableName);
                result.mergePattern(nodePattern);
            }
        }
        else 
        {
            variableName = "#COMPILER_v%s".formatted(anonCount);    
            nodePattern.variableName = Optional.of(variableName);
            result = new QueryNode(nodePattern, variableName, new ArrayList<>());
            graphMap.put(variableName, result);
            anonCount += 1;
        }
        return result;
    }

    // constructs graph, with mapping from variable name to node
    public static QueryGraph constructQueryGraph(List<PathPattern> patterns)
    {

        QueryGraph graph = new QueryGraph();

        for (PathPattern pathPattern : patterns)
        {
            List<PathComponent> components = pathPattern.pathSequence();

            assert components.get(0) instanceof NodePattern : "Bad initial path component, leading with edge or paren path not supported";
            NodePattern firstNodePattern = (NodePattern) components.get(0);
            QueryNode prior = graph.addQueryNode(firstNodePattern);

            for (int i = 1; i < components.size(); i += 2)
            {
                assert components.get(i) instanceof EdgePattern : "Bad path pattern";
                assert components.get(i+1) instanceof NodePattern : "Bad path pattern";
                EdgePattern currentEdgePattern = (EdgePattern) components.get(i);
                NodePattern currentNodePattern = (NodePattern) components.get(i+1);

                QueryNode next = graph.addQueryNode(currentNodePattern);

                QueryEdge edge = new QueryEdge(currentEdgePattern, new Pair<>(prior, next));
                prior.edges().add(edge);
                next.edges().add(edge);

                if (edge.variableName().isPresent())
                {
                    String variableName = edge.variableName().get();
                    if (graph.graphEdgeMap.containsKey(variableName))
                    {
                        throw new UnsupportedFeatureException("Multiple instances of single edge variable unsupported");
                    }

                    graph.graphEdgeMap.put(variableName, edge);
                }

                graph.edges.add(edge);

                prior = next;
            }
        }

        return graph;
    }
}
