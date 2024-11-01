package gql_gremlin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import ast.GqlProgram;
import ast.patterns.QualifiedPathPattern;
import data.Summary;
import data.SummaryStorage;

public interface Compiler {
    // optimises program (mutating) and then compiles it to a Gremlin traversal
    public GraphTraversal<Vertex, Map<String,Object>> compileToTraversal(GqlProgram program);
    
    // optimises program (mutating)
    public default void optimiseProgram(GqlProgram program) { return; }

    public static String getCompilerDescription(String compilerType)
    {
        switch (compilerType)
        {
            case "rigid": 
                return "Unoptimised Ordering";
            case "optimising":
            case "optimizing":
            case "o":
                return "Greedy Ordering";
            default:
                throw new RuntimeException("Unknown compiler type");
        }
    }

    public static Compiler getCompiler(String compilerType, String summaryName) 
        throws ClassNotFoundException, IOException
    {
        switch (compilerType)
        {
            case "rigid": 
                return new GremlinCompiler();
            case "optimising":
            case "optimizing":
            case "o":
                return new OptimisingCompiler(SummaryStorage.getSummary(summaryName));
            default:
                throw new RuntimeException("Unknown compiler type");
        }
    }
}
