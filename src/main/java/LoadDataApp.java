import java.util.List;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

import graphs.GremlinGraphFactory;
import graphs.JanusGremlinGraph;
import graphs.ResourcePaths;

public class LoadDataApp {
    // usage: <mode> <graph>
    // mode options:
    //  * "test", load, print and then drop data
    //  * "load", load and then print data
    //  * "print", print data, graph argument is ignored
    public static final String defaultMode = "test";
    public static final String defaultGraph = "g";
    public static final String remoteTraversalSourceName = "g";
    public static final String host = "localhost";
    public static final int port = 8182;



    public static void main(String[] args) throws Exception 
    {   
        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open("conf/remote-objects.yaml")
        );

        GraphTraversalSource gts = traversal().withRemote(connection);

        System.out.println(args);
        
        String mode = args.length > 0 ? args[0] : defaultMode;

        if (mode.equals("print"))
        {
            showGraph(gts);

            System.out.println("done");
            return;
        }

        String graph = args.length > 1 ? args[1] : defaultGraph;

        System.out.println("Loading graph " + graph + " to remote (block streamed).");
        loadGraph(gts, graph);

        System.out.println("Result: ");
        showGraph(gts);

        if (mode.equals("test"))
        {
            System.out.println("Clearing graph");
            dropGraph(gts);

            System.out.println("Result: ");
            showGraph(gts);
        }
        
        gts.close();
    }

    static void loadGraph(GraphTraversalSource gts, String graphName) throws Exception 
    {
        GremlinGraphFactory factory = new GremlinGraphFactory(ResourcePaths.getGraphFolder());
        JanusGremlinGraph graph = new JanusGremlinGraph(gts);
        factory.streamJsonToGraph(graph, graphName);

        // this loads the file's data into our remote graph
        // we don't actually use the resulting GremlinGraph object
    }

    static void dropGraph(GraphTraversalSource gts)
    {
        gts.V().drop().iterate();
    }

    static void showGraph(GraphTraversalSource gts)
    {
        List<Edge> edges = gts.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = gts.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));
    }
}
