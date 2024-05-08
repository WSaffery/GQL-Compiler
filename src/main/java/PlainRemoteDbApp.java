import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

import graphs.GremlinGraph;
import graphs.GremlinGraphFactory;
import graphs.JanusGremlinGraph;
import graphs.ResourcePaths;

public class PlainRemoteDbApp {
    // usage: graph1 graph2 ...
    public static final String defaultGraph = "g";
    public static final String remoteTraversalSourceName = "g";
    public static final String host = "localhost";
    public static final int port = 8182;



    public static void main(String[] args) throws Exception {

        // List<String> confs = map.containsKey("confs") ? map.get("confs") : List.of(defaultConfigFile);
        List<String> graphs = args.length > 0 ? Arrays.asList(args) : List.of(defaultGraph);

        System.out.println("graphs: " + graphs);

        // File f = new File("conf/remote-graph.properties");
        Path path = FileSystems.getDefault().getPath("conf", "remote-objects.yaml");
        System.out.println(Files.lines(path).toList());
        
        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open("conf/remote-objects.yaml")
        );

        GraphTraversalSource gts = traversal().withRemote(connection);

        for (String graph : graphs)
        {
            System.out.println();
            testGraph(gts, graph);
        }
        gts.close();
    }

    public static Map<String, List<String>> argsSplit(String[] args)
    {
        Map<String, List<String>> map = new HashMap<>();
        List<String> currentList = null;

        for (String arg : args)
        {
            if (arg.charAt(0) == '-')
            {
                String key = arg.charAt(1) == '-' ? arg.substring(2) : arg.substring(1);
                currentList = new ArrayList<>();
                map.put(key, currentList);
            }
            else if (currentList != null)
            {
                currentList.add(arg);
            }
        }

        return map;
    }

    static void testGraph(GraphTraversalSource gts, String graphName) throws Exception 
    {
        System.out.println("Loading graph " + graphName + " to remote.");
        GremlinGraphFactory factory = new GremlinGraphFactory(ResourcePaths.getGraphFolder());
        JanusGremlinGraph graph = new JanusGremlinGraph(gts);
        factory.readJsonToGraph(graph, graphName);

        // this loads the file's data into our remote graph
        // we don't actually use the resulting GremlinGraph object

        System.out.println("Result: ");
        showGraph(gts);

        System.out.println("Clearing graph");
        gts.V().drop().iterate();

        System.out.println("Result: ");
        showGraph(gts);
    }

    static void showGraph(GraphTraversalSource gts)
    {
        List<Edge> edges = gts.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = gts.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));
    }
}
