import java.io.FileNotFoundException;
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
import graphs.JanusGremlinGraph;
import graphs.ResourcePaths;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class RemoteDbApp {
    // usage: -confs conf1 conf2 ... -graphs graph1 graph2 ...
    // where conf_i is cluster yaml config files, ala remote-objects.yaml
    // and graph_j is graph names

    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultGraph = "g";

    public static void main(String[] args) throws Exception {
        Map<String, List<String>> map = argsSplit(args);

        List<String> confs = map.containsKey("confs") ? map.get("confs") : List.of(defaultConfigFile);
        List<String> graphs = map.containsKey("graphs") ? map.get("graphs") : List.of(defaultGraph);

        System.out.println("confs: " + confs);
        System.out.println("graphs: " + graphs);

        for (String conf : confs)
        {
            DriverRemoteConnection connection = DriverRemoteConnection.using(
                Cluster.open(conf)
             );

            GraphTraversalSource gts = traversal().withRemote(connection);

            for (String graph : graphs)
            {
                System.out.println();
                testGraph(gts, conf, graph);
            }
            gts.close();
        }
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
