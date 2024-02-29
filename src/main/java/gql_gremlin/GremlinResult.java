package gql_gremlin;

import java.util.ArrayList;

import org.apache.tinkerpop.gremlin.process.traversal.Bytecode;

public class GremlinResult {
    public Bytecode traversal;
    public ArrayList<String> graphNames = new ArrayList<>();
}