import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import javax.script.SimpleBindings;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalMetrics;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import  org.apache.tinkerpop.gremlin.groovy.jsr223.GremlinGroovyScriptEngine;

import ast.GqlProgram;
import cli.Arg;
import cli.CliArgParser;
import gql_gremlin.GremlinCompiler;

import static cli.display.DisplayHelpers.printMetrics;
import static cli.display.DisplayHelpers.printTable;
import static gql_gremlin.helpers.GremlinHelpers.appendTraversal;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import graphs.ResourcePaths;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class RunLsqbApp {
    // usage: -querydir querydir -conf conf -rts rts
    // where querydir is some query directory path relative to /src/test/resources/queries/
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and rts is the remote traversal source name, "g" by default

    public static final String defaultQueryDirectory = "gql/lsqb_optimized";
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultRTSName = "g";
    public static final String defaultLanguage = "gql";
    public static final String defaultOutputFile = ""; // stdout

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "querydir", Arg.single(defaultQueryDirectory),
        "conf", Arg.single(defaultConfigFile),
        "rts", Arg.single(defaultRTSName),
        "output", Arg.single(defaultOutputFile)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String queryDir = argParser.getArgSingle("querydir");
        String conf = argParser.getArgSingle("conf");
        String rts =  argParser.getArgSingle("rts"); // default = "g"
        PrintStream printStream = getPrintStream(argParser.getArgSingle("output"));
        String queryDirPath = ResourcePaths.getQueryFolder() + queryDir;

        System.out.println("conf: " + conf);
        System.out.println("queryDir: " + queryDir);
        System.out.println("queryDirPath: " + queryDirPath);

        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), rts
            );

        GraphTraversalSource gts = traversal().withRemote(connection);
        ;

        for (int i = 1; i < 9; i++)
        {
            String queryPath = queryDirPath + "//q" + i + ".gql";
            if (!new File(queryPath).exists())
            {
                System.out.println("Skipping query " + i + " file doesn't exist");
                continue;
            }
            
            final long startTime = System.currentTimeMillis();
            GqlProgram program = GqlProgram.buildProgram(queryPath);
            GremlinCompiler compiler = new GremlinCompiler();
            GraphTraversal<Vertex, Map<String,Object>> traversal = compiler.compileToTraversal(program);
            traversal = appendTraversal(gts, traversal.asAdmin().getBytecode());
            List<Map<String,Object>> res = traversal.toList();
            final long endTime = System.currentTimeMillis();

            assert res.size() == 1: "Too many result rows from query";
            assert res.get(0).size() == 1: "Too many result rows from query";
            Object resVal = res.get(0).values().toArray()[0];
            // assert resVal instanceof long : "Bad result, not a count";
            long count = (long) resVal;
            long duration = endTime - startTime;
            double durationSeconds = duration / 1000.0;

            printTable(List.of(Map.of(
                "Query", i,
                "Count", count,
                "Time (seconds)", durationSeconds
            )), printStream);
        }
        

        gts.close();
        
        // for some reason the program always hangs at the end, (it passes gts.close and all actual code)
        // probably something to do with maven not completing until all processes and subprocesses are done
        // and the remote process spawning a child that doesn't get killed until disconnected by the server
        // which seems to not happen on close like it should
    }

    static PrintStream getPrintStream(String filename) throws FileNotFoundException
    {
        if (filename.equals(""))
        {
            return System.out;
        }
        else 
        {
            return new PrintStream(new File(filename));   
        }
    }
}
