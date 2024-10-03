import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;


// import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
// import com.tinkerpop.rexster.OrientGraphConfiguration;

import cli.Arg;
import cli.CliArgParser;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import graphs.GremlinGraph;
import graphs.GremlinGraphFactory;
import graphs.GraphLoader;
import graphs.JanusGremlinGraph;
import graphs.OrientGremlinGraph;
import graphs.PropJanusGremlinGraph;
import graphs.ResourcePaths;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class RemoteDbApp {
    // usage: -mode mode [-customKeys] -conf conf -graph graph
    // where mode is either: print, load, or test
    // customKeys is false by default, and should be false if the database doesn't support custom keys (i.e. Janus) 
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and graph is graph names
    // default mode is test, default config file is "conf/remote-objects.yaml", default graph is "g"

    public static final String defaultMode = "test";
    public static final boolean defaultCustomKeys = false;
    public static final String defaultSystem = "janus";
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultGraph = "g";

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "mode", Arg.single(defaultMode),
        "customKeys", Arg.flag(),
        "system", Arg.single(defaultSystem),
        "conf", Arg.single(defaultConfigFile),
        "graph", Arg.single(defaultGraph)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String mode = argParser.getArgSingle("mode");
        String system = argParser.getArgSingle("system");
        String conf = argParser.getArgSingle("conf");

        System.out.println("conf: " + conf);

        // OrientGraph OGraph;
        GraphTraversalSource gts;
        // if (system.equals("orient"))
        // {
        //     // config = new BaseConfiguration();
            
        //     Configuration config = new BaseConfiguration();
        //     config = new BaseConfiguration();
        //     config.setProperty("orient-url","remote:localhost/demodb");
        //     config.setProperty("orient-transactional",true);
        //     config.setProperty("orient-user", "root");
        //     config.setProperty("orient-pass", "root1234");
        //     // Configuration 

        //     OGraph = OrientGraph.open(config);
        //     gts = OGraph.traversal();
        // }
        // else 
        // {
            DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), "g"
            );

            gts = traversal().withRemote(connection);
        // }
        
            
        // graph independent operation modes
        if (mode.equals("print"))
        {
            System.out.println("Showing graph with config: " + conf);
            showGraph(gts);
        }
        else if (mode.equals("drop"))
        {
            System.out.println("Dropping graph with config: " + conf);
            dropGraph(gts);
        }
        else 
        {
            String graph = argParser.getArgSingle("graph");
            boolean customKeys = argParser.checkFlagged("customKeys");
            System.out.println("graph: " + graph);
            System.out.println("customKeys: " + customKeys);


            System.out.println();
            if (mode.equals("test"))
            {
                System.out.println("Loading graph " + graph + " with config: " + conf);
                loadGraph(gts, graph, customKeys);
                System.out.println("Showing Load Result");
                showGraph(gts);
                System.out.println("Clearing graph");
                dropGraph(gts);
                System.out.println("Showing Clear Result");
                showGraph(gts);
            }
            else if (mode.equals("load"))
            {
                System.out.println("Loading graph " + graph + " with config: " + conf);
                loadGraph(gts, graph, customKeys);
            }
            
            System.out.println("Finished for graph " + graph + " with config: " + conf);
        }
        // gts.close();

        gts.tx().commit();
        gts.close();
        
        // for some reason the program always hangs at the end, (it passes gts.close and all actual code)
        // probably something to do with maven not completing until all processes and subprocesses are done
        // and the remote process spawning a child that doesn't get killed until disconnected by the server
        // which seems to not happen on close like it should
    }


    static void testGraph(GraphTraversalSource gts, String configFileName, String graphName) throws Exception 
    {
        System.out.println("Loading graph " + graphName + " with config: " + configFileName);
        GremlinGraphFactory factory = new GremlinGraphFactory(ResourcePaths.getGraphFolder());
        JanusGremlinGraph graph = new JanusGremlinGraph(gts);
        
        factory.loadJsonToGraph(graph, graphName);

        // this loads the file's data into our remote graph
        // we don't actually use the resulting GremlinGraph object

        System.out.println("Result: ");
        showGraph(gts);

        System.out.println("Clearing graph");
        gts.V().drop().iterate();

        System.out.println("Result: ");
        showGraph(gts);
    }

    static void loadGraph(GraphTraversalSource gts, String graphName, boolean customKeys) throws Exception
    {
        GremlinGraphFactory factory = new GremlinGraphFactory(ResourcePaths.getGraphFolder());
        // ordinary
        GraphLoader graph = customKeys ? new GremlinGraph(gts) : new JanusGremlinGraph(gts);
        // test orient
        // GraphLoader graph = new OrientGremlinGraph(gts);
        factory.loadJsonToGraph(graph, graphName);
    }

    static void showGraph(GraphTraversalSource gts)
    {
        List<String> edges = gts.E().label().toList();
        HashSet<String> edgeSet = new HashSet<>(edges);
        edgeSet.forEach(e -> System.out.println(e.toString()));
        // edges.forEach(e -> System.out.println(e.toString()));
        
        // List<Vertex> vertexs = gts.V().toList();
        // vertexs.forEach(v -> System.out.println(v.toString()));
    }

    static void dropGraph(GraphTraversalSource gts)
    {
        gts.V().drop().iterate();
        // gts.E().drop().iterate(); // this is necessary for arcadedb, but shouldn't be
    }
}
