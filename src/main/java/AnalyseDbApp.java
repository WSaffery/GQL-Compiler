import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.SimpleBindings;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalMetrics;
import org.apache.tinkerpop.gremlin.structure.Column;
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

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;


// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class AnalyseDbApp {
    // usage: -query query -conf conf -rts rts
    // where query is some query path relative to /src/test/resources/queries/
    // where conf is cluster yaml config files, ala remote-objects.yaml
    // and rts is the remote traversal source name, "g" by default

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

        System.out.println("conf: " + conf);

        DriverRemoteConnection connection = DriverRemoteConnection.using(
            Cluster.open(conf), rts
            );

        GraphTraversalSource gts = traversal().withRemote(connection);
        ;

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

        System.out.println("Value, Count");
        for (Entry<Object, Long> count : result.get(0).entrySet())
        {
            System.out.println(count.getKey() + ", " + count.getValue());
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
