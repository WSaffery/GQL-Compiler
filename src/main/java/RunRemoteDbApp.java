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
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalExplanation;
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

public class RunRemoteDbApp {
    // usage: -query query -conf conf -rts rts
    // where query is some query path relative to /src/test/resources/queries/
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and rts is the remote traversal source name, "g" by default

    public static final String defaultQuery = "gql/noop.gql";
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultRTSName = "g";
    public static final String defaultLanguage = "gql";
    public static final String defaultOutputFile = ""; // stdout

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "query", Arg.single(defaultQuery),
        "conf", Arg.single(defaultConfigFile),
        "rts", Arg.single(defaultRTSName),
        "output", Arg.single(defaultOutputFile),
        "language", Arg.single(defaultLanguage),
        "profile", Arg.flag(),
        "explain", Arg.flag()
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String queryArg = argParser.getArgSingle("query");
        String conf = argParser.getArgSingle("conf");
        String rts =  argParser.getArgSingle("rts"); // default = "g"
        boolean profile = argParser.checkFlagged("profile");
        boolean explain = argParser.checkFlagged("explain");
        PrintStream printStream = getPrintStream(argParser.getArgSingle("output"));

        String queryPath = ResourcePaths.getQueryFolder() + queryArg;
        String queryLanguage = argParser.getArgSingle("language");

        System.out.println("conf: " + conf);
        System.out.println("query: " + queryArg);
        System.out.println("queryPath: " + queryPath);

        assert (!explain || !profile) : "Can't both profile and explain the query";

        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), rts
            );

        GraphTraversalSource gts = traversal().withRemote(connection);
        ;

        if (queryLanguage.equals("gql"))
        {
            final long startTime = System.currentTimeMillis();

            GqlProgram program = GqlProgram.buildProgram(queryPath);
            GremlinCompiler compiler = new GremlinCompiler();
            GraphTraversal<Vertex, Map<String,Object>> traversal = compiler.compileToTraversal(program);
            traversal = appendTraversal(gts, traversal.asAdmin().getBytecode());
            final long compileEndTime = System.currentTimeMillis();
            if (profile)
            {
                List<TraversalMetrics> metrics = traversal.profile().toList();
                assert metrics.size() == 1 : "multiple metrics returned by profile";
                // printStream.println(metrics.get(0));
                printMetrics(metrics.get(0), printStream);
            }
            else if (explain)
            {
                TraversalExplanation explanation = traversal.explain();
                printStream.println(explanation.prettyPrint());
            }
            else {
                List<Map<String,Object>> res = null;
                try {
                    res = traversal.toList();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                final long endTime = System.currentTimeMillis();
                final long duration = endTime - startTime;
                final double durationSeconds = duration / 1000.0;
                final long compDuration = compileEndTime - startTime;
                final double compDurationSeconds = compDuration / 1000.0;

                if (res != null)
                {
                    printTable(res, printStream);
                }
                else 
                {
                    printStream.println("Query failed");
                }
                printStream.printf("Compiled in %.4f seconds\n", compDurationSeconds);
                printStream.printf("Finished in %.4f seconds\n", durationSeconds);
            }
        }
        else if (queryLanguage.equals("gremlin"))
        {
            // GroovyTranslator translator = GroovyTranslator.of("g");
            // translator.
            // traversal = gts.
            GremlinGroovyScriptEngine gremlinGroovyScriptEngine = new GremlinGroovyScriptEngine();
            SimpleBindings bindings = new SimpleBindings();
            bindings.put("g", gts);
            FileReader reader = new FileReader(new File(queryPath));
            
            GraphTraversal<?, ?> traversal = (GraphTraversal<?, ?>) gremlinGroovyScriptEngine.eval(reader, bindings);
            // CompiledScript script = gremlinGroovyScriptEngine.compile();
            // script.
            if (profile)
            {
                List<TraversalMetrics> metrics = traversal.profile().toList();
                assert metrics.size() == 1 : "multiple metrics returned by profile";
                // System.out.println(metrics);
                // printStream.println(metrics.get(0));
                printMetrics(metrics.get(0), printStream);
            }
            else if (explain)
            {
                TraversalExplanation explanation = traversal.explain();
                System.out.println(explanation.prettyPrint());
            }
            else 
            {
                List<?> res = traversal.toList();
                for (Object obj : res) 
                {
                    printStream.println(obj);
                }
            }
        }
        else 
        {
            throw new RuntimeException("Bad query language specified");
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
