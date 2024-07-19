import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import ast.GqlProgram;
import cli.Arg;
import cli.CliArgParser;
import gql_gremlin.GremlinCompiler;

import static cli.display.DisplayHelpers.printTable;
import static gql_gremlin.helpers.GremlinHelpers.appendTraversal;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import graphs.ResourcePaths;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class RunRemoteDbApp {
    // usage: -query query -conf conf -rts rts
    // where query is some query path relative to /src/test/resources/queries/
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and rts is the remote traversal source name, "g" by default

    public static final String defaultQuery = "gql/noop.gql";
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultRTSName = "g";

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "query", Arg.single(defaultQuery),
        "conf", Arg.single(defaultConfigFile),
        "rts", Arg.single(defaultRTSName)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String queryArg = argParser.getArgSingle("query");
        String conf = argParser.getArgSingle("conf");
        String rts =  argParser.getArgSingle("rts"); // default = "g"

        String queryPath = ResourcePaths.getQueryFolder() + queryArg;
        
        System.out.println("conf: " + conf);
        System.out.println("query: " + queryArg);
        System.out.println("queryPath: " + queryPath);

        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), rts
            );

        GraphTraversalSource gts = traversal().withRemote(connection);
        
        GqlProgram program = GqlProgram.buildProgram(queryPath);
        GremlinCompiler compiler = new GremlinCompiler();
        GraphTraversal<Vertex, Map<String,Object>> traversal = compiler.compileToTraversal(program);

        traversal = appendTraversal(gts, traversal.asAdmin().getBytecode());
        
        List<Map<String,Object>> res = traversal.toList();

        printTable(res);

        gts.close();
        
        // for some reason the program always hangs at the end, (it passes gts.close and all actual code)
        // probably something to do with maven not completing until all processes and subprocesses are done
        // and the remote process spawning a child that doesn't get killed until disconnected by the server
        // which seems to not happen on close like it should
    }
}
