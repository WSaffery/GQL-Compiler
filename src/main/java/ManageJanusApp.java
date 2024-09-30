import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import cli.Arg;
import cli.CliArgParser;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.schema.JanusGraphManagement;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class ManageJanusApp {
    // usage: -mode mode [-customKeys] -conf conf -graph graph
    // where mode is either: print, load, or test
    // customKeys is false by default, and should be false if the database doesn't support custom keys (i.e. Janus) 
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and graph is graph names
    // default mode is test, default config file is "conf/remote-objects.yaml", default graph is "g"

    public static final String defaultConfigFile = "conf/remote-objects.yaml";

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "conf", Arg.single(defaultConfigFile)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String conf = argParser.getArgSingle("conf");

        System.out.println("conf: " + conf);

        Cluster cluster = Cluster.open(conf);
        Client client = cluster.connect();

        ResultSet results = client.submit("""
        
        ManagementSystem.awaitGraphIndexStatus(graph, 'byPlabelComposite').call()
        mgmt = graph.openManagement()
        mgmt.updateIndex(mgmt.getGraphIndex("byPlabelComposite"), SchemaAction.REINDEX).get()
        """);
        // JanusGraphManagement
        System.out.println(results);

        results.forEach(r -> System.out.println(r));
    }
}
