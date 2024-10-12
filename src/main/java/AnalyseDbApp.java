import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Column;
import cli.Arg;
import cli.CliArgParser;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;


// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class AnalyseDbApp {
    // usage: -conf conf -rts rts -output outputFile
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and rts is the remote traversal source name, "g" by default
    // and output is the (standard) output file, defaults to standard out
    
    public static final String defaultConfigFile = "conf/remote-objects.yaml";
    public static final String defaultRTSName = "g";
    public static final String defaultOutputFile = ""; // stdout

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "conf", Arg.single(defaultConfigFile),
        "rts", Arg.single(defaultRTSName),
        "output", Arg.single(defaultOutputFile)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        String conf = argParser.getArgSingle("conf");
        String rts =  argParser.getArgSingle("rts"); // default = "g"
        PrintStream printStream = getPrintStream(argParser.getArgSingle("output"));

        printStream.println("conf: " + conf);

        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), rts
            );

        GraphTraversalSource gts = traversal().withRemote(connection);
        ;

        // ----
        // NODES
        // ----

        {
            printStream.println("Node Labels");
            List<Map<Object,Long>> result = gts.V().groupCount().by(label()).toList();
    
            assert result.size() == 1 : "More results than expected";
    
            printStream.println("Value, Count");
            for (Entry<Object, Long> count : result.get(0).entrySet())
            {
                printStream.println(count.getKey() + ", " + count.getValue());
            }
        }

        // ----
        // TRIPLETS
        // ----
        
        {
            printStream.println("Triplets");
            // traverse from (u)->(e)->(v), return counts grouped by labels of u, e, and v
            List<Map<Object,Long>> result = gts.V().as("u").
            outE().as("e").
            inV().as("v").
            groupCount().
            by(
                select("u","e","v").
                by(label()).
                select(Column.values)
            ).toList();
    
            assert result.size() == 1 : "More results than expected";
    
            printStream.println("Value, Count");
            for (Entry<Object, Long> count : result.get(0).entrySet())
            {
                printStream.println(count.getKey() + ", " + count.getValue());
            }
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
