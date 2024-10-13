package gql_gremlin.query;

import java.util.List;
import java.util.Set;

import ast.patterns.NodePattern;
import exceptions.SemanticErrorException;

public class QueryNode {
    NodePattern pattern;
    String variableName;
    List<QueryEdge> edges;
    Set<String> labels;

    public QueryNode(NodePattern pattern, String variableName, List<QueryEdge> edges)
    {
        this.pattern = pattern;
        this.variableName = variableName;
        this.edges = edges;
        this.labels = QueryElement.calculateLabels(pattern.labelPattern());
    }

    public NodePattern pattern() { return pattern; }
    public String variableName() {return variableName; }
    public List<QueryEdge> edges() {return edges; }
    public Set<String> labels() { return labels; }

    public void mergePattern(NodePattern newPattern)
    {
        if (pattern == newPattern) return;

        Set<String> newLabels = QueryElement.calculateLabels(newPattern.labelPattern());

        if (labels.isEmpty())
        {
            labels = newLabels;
        }
        else if (!newLabels.isEmpty() && !labels.equals(newLabels)) {
            // TODO! could take the intersection of all the label sets from each capture here instead
            throw new SemanticErrorException("Two or more instances of a variable with degenerate label expressions");
        }
    }

    public String toString()
    {
        return String.format("QueryNode[pattern=%s, variableName=%s, edges=%s, labels=%s]", pattern, variableName, edgesString(), labels);
    }

    String edgesString()
    {
        return "[" + String.join(", ", edges.stream().map(e -> e.toStringShallow()).toList()) + "]";
    }

    public String toStringShallow()
    {
        return String.format("QueryNode[variableName=%s]", variableName);
    }
}
