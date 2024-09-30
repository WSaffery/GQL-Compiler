import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import ast.GqlProgram;
import cli.Arg;
import cli.CliArgParser;
import gql_gremlin.GremlinCompiler;

import static gql_gremlin.helpers.GremlinHelpers.appendTraversal;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import graphs.ResourcePaths;
import java.util.Collections;

import java.nio.file.Path;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

record Result(
    String system, String systemVariant, double scaleFactor, int queryId, 
    double duration, long count
)
{
    Result(String system, double scaleFactor, int queryId, double duration, long count)
    {
        this(system, "", scaleFactor, queryId, duration, count);
    }

    public String toTsvString()
    {
        return String.format("%s\t%s\t%s\t%s\t%.4f\t%s", 
            system, systemVariant, scaleFactor, queryId, duration, count);
    }
}

public class RunLsqbApp {
    // usage: -querydir querydir -conf conf -rts rts
    // where querydir is some query directory path relative to /src/test/resources/queries/
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and rts is the remote traversal source name, "g" by default

    public static final String defaultQueryDirectory = "gql/lsqb_optimised";
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultRTSName = "g";
    public static final String defaultOutputFile = ""; // stdout
    public static final String defaultSeed = "123";
    public static final String defaultRepetitions = "10";
    public static final String defaultVariant = "";
    public static final String defaultResultsFile = "results/results.csv";


    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "querydir", Arg.single(defaultQueryDirectory),
        "conf", Arg.single(defaultConfigFile),
        "rts", Arg.single(defaultRTSName),
        "output", Arg.single(defaultOutputFile),
        "seed", Arg.single(defaultSeed),
        "repetitions", Arg.single(defaultRepetitions),
        "variant", Arg.single(defaultVariant),
        "results", Arg.single(defaultResultsFile)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String queryDir = argParser.getArgSingle("querydir");
        String conf = argParser.getArgSingle("conf");
        String rts =  argParser.getArgSingle("rts"); // default = "g"
        PrintStream printStream = getPrintStream(argParser.getArgSingle("output"));
        String resultsFileString = argParser.getArgSingle("results");
        Path resultsFilePath = Path.of(resultsFileString);
        
        assert resultsFilePath.getParent().toFile().isDirectory() : "Results directory does not exist";
        

        String queryDirPath = ResourcePaths.getQueryFolder() + queryDir;
        System.out.println("conf: " + conf);
        System.out.println("queryDir: " + queryDir);
        System.out.println("queryDirPath: " + queryDirPath);

        String variant = argParser.getArgSingle("variant");
        Integer reptitions = Integer.parseInt(argParser.getArgSingle("repetitions"));
        Integer seed = Integer.parseInt(argParser.getArgSingle("seed"));


        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), rts
            );

        GraphTraversalSource gts = traversal().withRemote(connection);
        ;

        String system = "GqlToGremlin";
        double scaleFactor = 0.1;

        Random rnd = new Random(seed);
        
        ArrayList<Result> results = new ArrayList<>();
        for (int rep = 0; rep < reptitions; rep++)
        {
            List<Integer> queryIds = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8 ,9));
            Collections.shuffle(queryIds, rnd);
            for (Integer i : queryIds)
            {
                String queryPath = queryDirPath + "//q" + i + ".gql";
                System.out.println(queryPath);
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
                
                results.add(
                    new Result(system, variant, scaleFactor, i, durationSeconds, count)
                );
                printStream.printf("Query %d done, Count=%d, Time (seconds) = %.4f\n", 
                    i, count, durationSeconds
                );
            }
        }
        
        

        gts.close();

        File resultsFile = resultsFilePath.toFile();
        PrintStream resultsPrintStream = new PrintStream(new FileOutputStream(resultsFile, true));
        for (Result result : results)
        {
            resultsPrintStream.println(result.toTsvString());
        }
        resultsPrintStream.close();
        
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
