import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import graphs.GremlinGraph;
import graphs.GremlinGraphFactory;
import graphs.GraphLoader;
import graphs.JanusGremlinGraph;
import graphs.ResourcePaths;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class TestRemoteDbApp {
    // usage: -mode mode [-customKeys true/false] -confs conf1 conf2 ... -graphs graph1 graph2 ...
    // where mode is either: print, load, or test
    // customKeys is false by default, and should be false if the database doesn't support custom keys (i.e. Janus) 
    // where conf_i is cluster yaml config files, ala remote-objects.yaml
    // and graph_j is graph names

    public static final String defaultMode = "test";
    public static final boolean defaultCustomKeys = false;
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultGraph = "g";

    public static void main(String[] args) throws Exception {
        Map<String, List<String>> map = argsSplit(args);

        String mode = map.containsKey("mode") ? map.get("mode").get(0) : defaultMode;
        boolean customKeys = map.containsKey("customKeys") ? map.get("customKeys").get(0).equals("true") : defaultCustomKeys;
        List<String> confs = map.containsKey("confs") ? map.get("confs") : List.of(defaultConfigFile);
        List<String> graphs = map.containsKey("graphs") ? map.get("graphs") : List.of(defaultGraph);

        System.out.println("confs: " + confs);
        System.out.println("graphs: " + graphs);

        for (String conf : confs)
        {
            DriverRemoteConnection connection = DriverRemoteConnection.using(
                Cluster.open(conf), "g"
             );

            GraphTraversalSource gts = traversal().withRemote(connection);
            
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
                for (String graph : graphs) // graph dependent operation modes
                {
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
            }
            gts.close();
        }
        
        // for some reason the program always hangs at the end, (it passes gts.close and all actual code)
        // probably something to do with maven not completing until all processes and subprocesses are done
        // and the remote process spawning a child that doesn't get killed until disconnected by the server
        // which seems to not happen on close like it should
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
