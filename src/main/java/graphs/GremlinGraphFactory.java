package graphs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.start;

import exceptions.InvalidEdgeFormatException;
import exceptions.InvalidNodeFormatException;
import json.gremlin.EdgeParser;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;
import json.gremlin.NodeParser;

public class GremlinGraphFactory {
    private static final String NODE_FILE = "nodes.json";
    private static final String EDGE_FILE = "edges.json";

    private static final String NODE_STREAM_FILE = "node_stream.json";
    private static final String EDGE_STREAM_FILE = "edge_stream.json";
    private String graphDir;

    public GremlinGraphFactory(String graphDir)
    {
        assert(graphDir.charAt(graphDir.length()-1) == '/'); // ensure trailing slash
        this.graphDir = graphDir;
    }

    // read or stream json to graph, preferring stream when possible
    public void loadJsonToGraph(GraphLoader graph, String fileName)
        throws InvalidNodeFormatException, InvalidEdgeFormatException, IOException 
    {
        assert(fileName.charAt(fileName.length()-1) != '/'); // ensure no trailing slash
        Path nodeStreamPath = Path.of(graphDir, fileName, NODE_STREAM_FILE);
        Path edgeStreamPath = Path.of(graphDir, fileName, EDGE_STREAM_FILE);
        if (Files.exists(nodeStreamPath) && Files.exists(edgeStreamPath))
        {
            streamJsonToGraph(graph, fileName);
        }
        else 
        {
            readJsonToGraph(graph, fileName);
        }
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

    public void streamJsonToGraph(GraphLoader graph, String fileName)
        throws InvalidNodeFormatException, InvalidEdgeFormatException, IOException
    {
        assert(fileName.charAt(fileName.length()-1) != '/'); // ensure no trailing slash
        String path = graphDir + fileName;

        Stream<JsonNode> nodes = new NodeParser(path + "/" + NODE_STREAM_FILE).getNodeStream();
        Stream<JsonEdge> edges = new EdgeParser(path + "/" + EDGE_STREAM_FILE).getEdgeStream();

        graph.streamJsonGraph(nodes, edges);
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
