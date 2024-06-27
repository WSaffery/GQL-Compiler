package ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ast.queries.GqlBody;
import ast.queries.GqlQuery;
import ast.queries.QueryConjunctor;
import ast.variables.GqlVariables;

// conjunctions and queries are stored flat file in order of appearance.
// query1 conjunction1 query2 conjunction2 query3 ...
// conjunctions[i] conjoins queries[i] and queries[i+1] 
public class GqlProgram {
    public Optional<String> graphName = Optional.empty();
    public GqlVariables variables = new GqlVariables();
    public GqlBody body = new GqlBody();
}