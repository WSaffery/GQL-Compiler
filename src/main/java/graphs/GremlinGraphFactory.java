package graphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import exceptions.InvalidEdgeFormatException;
import exceptions.InvalidNodeFormatException;
import json.gremlin.EdgeParser;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;
import json.gremlin.NodeParser;

public class GremlinGraphFactory {
    private static final String NODE_FILE = "nodes.json";
    private static final String EDGE_FILE = "edges.json";
    private String graphDir;

    public GremlinGraphFactory(String graphDir)
    {
        assert(graphDir.charAt(graphDir.length()-1) == '/'); // ensure trailing slash
        this.graphDir = graphDir;
    }

    public GremlinGraph makeGremlinGraph(Graph graph, String name) 
        throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException
    {
        assert(name.charAt(name.length()-1) != '/'); // ensure no trailing slash
        String path = graphDir + name;

        ArrayList<JsonNode> nodes = new NodeParser(path + "/" + NODE_FILE).getNodes();
        ArrayList<JsonEdge> edges = new EdgeParser(path + "/" + EDGE_FILE).getEdges();

        return GremlinGraph.makGremlinGraph(graph, nodes, edges);
    }

    public GremlinGraph makeGremlinGraph(String name) 
        throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException
    {
        return makeGremlinGraph(TinkerGraph.open(), name);
    }
}
