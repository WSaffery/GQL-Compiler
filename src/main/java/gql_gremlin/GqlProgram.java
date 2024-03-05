package gql_gremlin;

import java.util.ArrayList;
import java.util.List;

import gql.enums.QueryConjunctor;

// conjunctions and queries are stored flat file in order of appearance.
// query1 conjunction1 query2 conjunction2 query3 ...
// conjunctions[i] conjoins queries[i] and queries[i+1] 
public class GqlProgram {
    public String graph = null;
    public List<GqlQuery> queries = new ArrayList<GqlQuery>();
    public List<QueryConjunctor> conjunctions = new ArrayList<QueryConjunctor>();
}