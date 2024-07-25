import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import cli.Arg;
import cli.CliArgParser;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import graphs.GremlinGraph;
import graphs.GremlinGraphFactory;
import graphs.GraphLoader;
import graphs.JanusGremlinGraph;
import graphs.ResourcePaths;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

// runs the load-drop test on every pair of given configs and graphs
// batch version of RemoteDbApp for test mode
public class TestRemoteDbApp {
    // usage: -customKeys val1 val2 ... -confs conf1 conf2 ... -graph graph1 graph2 ...
    // customKeys is "false" by default, and should be false if the database doesn't support custom keys (i.e. Janus) 
    // setting a single value of customKeys broadcasts to all configs, otherwise they are applied pairwise 
    // confs are cluster yaml config files, ala remote-objects.yaml
    // and graphs are graph names
    // default config file is just "conf/remote-objects.yaml", default graph is just "g"

    public static final String defaultCustomKeys = "false";
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultGraph = "g";

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "customKeys", Arg.multi(defaultCustomKeys),
        "conf", Arg.multi(defaultConfigFile),
        "graphs", Arg.multi(defaultGraph)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        List<String> confs = argParser.getArgs("conf");
        List<String> graphs = argParser.getArgs("graphs");
        List<String> customKeys = argParser.getArgs("customKeys");

        System.out.println("conf: " + confs);
        System.out.println("graphs: " + graphs);

        if (customKeys.size() == 1 && confs.size() > 1)
        {
            String base = customKeys.get(0);
            for (int i = 0; i < confs.size() - 1; i++)
            {
                customKeys.add(base);
            }
        }

        for (int i = 0; i < confs.size(); i++)
        {
            String conf = confs.get(i);
            boolean customKeyConf = Boolean.valueOf(customKeys.get(i));
            DriverRemoteConnection connection = DriverRemoteConnection.using(
                Cluster.open(conf), "g"
                );
            
            GraphTraversalSource gts = traversal().withRemote(connection);
            
            System.out.println("Clearing db with config: " + conf);
            dropGraph(gts);
            System.out.println("Showing Clear Result");
            showGraph(gts);

            for (String graph : graphs)
            {
                System.out.println("Loading graph " + graph + " with config: " + conf);
                loadGraph(gts, graph, customKeyConf);
                System.out.println("Showing Load Result");
                showGraph(gts);
                System.out.println("Clearing graph");
                dropGraph(gts);
                System.out.println("Showing Clear Result");
                showGraph(gts);

                System.out.println("Finished for graph " + graph + " with config: " + conf);
            }

            gts.close();

        }
        
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
        GraphLoader graph = customKeys ? new GremlinGraph(gts) : new JanusGremlinGraph(gts);
        factory.loadJsonToGraph(graph, graphName);
    }

    static void showGraph(GraphTraversalSource gts)
    {
        List<Edge> edges = gts.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = gts.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));
    }

    static void dropGraph(GraphTraversalSource gts)
    {
        gts.V().drop().iterate();
        // gts.E().drop().iterate(); // this is necessary for arcadedb, but shouldn't be
    }
}
