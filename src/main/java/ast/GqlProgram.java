package ast;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr.GqlLexer;
import antlr.GqlParser;
import ast.queries.GqlBody;
import ast.queries.GqlQuery;
import ast.queries.QueryConjunctor;
import ast.variables.GqlVariables;
import ast.visitors.AstListener;
import ast.patterns.QualifiedPathPattern;


// conjunctions and queries are stored flat file in order of appearance.
// query1 conjunction1 query2 conjunction2 query3 ...
// conjunctions[i] conjoins queries[i] and queries[i+1] 
public class GqlProgram {
    public Optional<String> graphName = Optional.empty();
    public GqlVariables variables = new GqlVariables();
    public GqlBody body = new GqlBody();

    public static GqlProgram buildProgram(String path) throws IOException
    {
        GqlLexer lexer = new GqlLexer(CharStreams.fromFileName(path));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GqlParser parser = new GqlParser(tokens);
        
        ParseTree antlrAST = parser.query();
        AstListener listener = new AstListener();
        ParseTreeWalker.DEFAULT.walk(listener, antlrAST);
        GqlProgram program = listener.GetResult();
        
        return program;
    }

    public void describeOrdering(PrintStream stream)
    {
        for (int i = 0; i < body.getQueryCount() - 1; i++)
        {
            GqlQuery query = body.getQuery(i);
            QueryConjunctor conjunctor = body.getConjunctor(i);
            List<String> paths = query.matchExpressions.stream().flatMap(m -> m.pathPatterns.stream().map(qp -> qp.toOrdering())).toList();
            String orderings = String.join(",", paths);
            stream.printf("%s %s\n", orderings, conjunctor.toString());
        }
        GqlQuery query = body.getQuery(body.getQueryCount() - 1);
        List<String> paths = query.matchExpressions.stream().flatMap(m -> m.pathPatterns.stream().map(qp -> qp.toOrdering())).toList();
        String orderings = String.join(",", paths);
        stream.println(orderings);
    }
}