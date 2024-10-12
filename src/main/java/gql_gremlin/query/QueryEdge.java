package gql_gremlin.query;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;

import ast.patterns.EdgePattern;
import ast.patterns.NodePattern;
import exceptions.SemanticErrorException;

public class QueryEdge {
    EdgePattern pattern;
    Pair<QueryNode, QueryNode> nodes;
    Set<String> labels;
    QueryEdge origin;
    QueryEdge reversed;

    private QueryEdge(EdgePattern pattern, Pair<QueryNode, QueryNode> nodes, 
                      Set<String> labels, QueryEdge origin, QueryEdge reversed)
    {
        this.pattern = pattern;
        this.nodes = nodes;
        this.labels = labels;
        this.origin = origin;
        this.reversed = reversed;
    }

    public QueryEdge(EdgePattern pattern, Pair<QueryNode, QueryNode> nodes)
    {
        this.pattern = pattern;
        this.nodes = nodes;
        this.labels = QueryElement.calculateLabels(pattern.labelPattern()); 
        this.origin = this;
        this.reversed = makeReversed();
    }

    QueryEdge makeReversed() 
    {
        // our reverse is a new inverted query edge 
        // which when queried will return this as its origin and reverse
        return new QueryEdge(
            pattern.reversed(), Pair.with(nodes.getValue1(), nodes.getValue0()), 
            new HashSet<>(labels), this, this);
    }

    public QueryEdge origin()
    {
        return origin;
    }

    public QueryEdge reversed()
    {
        return reversed;
    }

    public Pair<QueryNode, QueryNode> nodes() { return nodes; }
    public EdgePattern pattern() { return pattern; }
    public Optional<String> variableName() { return pattern.variableName(); }
    public Set<String> labels() { return labels; }

    public String toString()
    {
        return toStringShallow();
    }

    public String toStringShallow()
    {
        return String.format("QueryEdge[start=%s, end=%s, labels=%s]", nodes.getValue0().variableName, nodes.getValue1().variableName, labels);
    }




}
