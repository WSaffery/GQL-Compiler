package graphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

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

    public void readJsonToGraph(GraphLoader graph, String fileName) 
        throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException
    {
        assert(fileName.charAt(fileName.length()-1) != '/'); // ensure no trailing slash
        String path = graphDir + fileName;

        ArrayList<JsonNode> nodes = new NodeParser(path + "/" + NODE_FILE).getNodes();
        ArrayList<JsonEdge> edges = new EdgeParser(path + "/" + EDGE_FILE).getEdges();

        graph.loadJsonGraph(nodes, edges);
    }

    public GremlinGraph makeGremlinGraph(GraphTraversalSource source, String fileName) 
        throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException
    {
        GremlinGraph gremlinGraph = new GremlinGraph(source);
        readJsonToGraph(gremlinGraph, fileName);
        return gremlinGraph;
    }

    public GremlinGraph makeGremlinGraph(Graph graph, String fileName) 
        throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException
    {
        return makeGremlinGraph(traversal().withEmbedded(graph), fileName);
    }

    public GremlinGraph makeGremlinGraph(String configFile, String fileName) 
        throws Exception
    {
        return makeGremlinGraph(traversal().withRemote(configFile), fileName);
    }
    

    public GremlinGraph makeGremlinGraph(String fileName) 
        throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException
    {
        return makeGremlinGraph(TinkerGraph.open(), fileName);
    }

}
