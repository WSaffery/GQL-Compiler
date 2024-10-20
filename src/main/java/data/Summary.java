package data;
import java.io.Serializable;
import java.util.Map;

import org.javatuples.Triplet;

import gql_gremlin.query.QueryEdge;
import gql_gremlin.query.QueryNode;

// cardinalities of nodes by label, and triplets (node_a, edge, node_b) by labels
public record Summary(
    Map<String, Long> nodeCardinalities,
    Map<Triplet<String, String, String>, Long> tripletCardinalities
) implements Serializable {

    public Long getCardinality(QueryNode node)
    {
        return node.labels().
            stream().
            map(s -> nodeCardinalities.get(s)).
            reduce(0L, (a, b) -> a + b);
    }

    public Long getCardinality(String node)
    {
        return nodeCardinalities.get(node);
    }

    // get average cardinality of triplet, starting from node A 
    public Double getAvgCardinality(QueryNode nodeA, QueryEdge edge, QueryNode nodeB)
    {
        Double cost = 0d;
        // assert edge.pattern().labelPattern() instanceof Label 
        // String labelEdge = "";

        for (String labelA : nodeA.labels())
        {
            for (String labelEdge : edge.labels())
            {
                for (String labelB : nodeB.labels())
                {
                    cost += getCardinality(labelA, labelEdge, labelB).doubleValue() 
                            / getCardinality(labelA).doubleValue();
                }
            }
        }

        return cost;
    }

    // get the rotation invariant cardinality of the triplet
    public Long getCardinality(String a, String edge, String b)
    {
        if (tripletCardinalities.containsKey(Triplet.with(a, edge, b)))
        {
            return tripletCardinalities.get(Triplet.with(a, edge, b));
        }
        else 
        {
            // throws if non-existent
            return tripletCardinalities.get(Triplet.with(b, edge, a));
        }
    }

    public static Summary getLsqb01Summary()
    {
        return new Summary(
            Map.ofEntries(
                Map.entry("Comment", 215488L),
                Map.entry("Company", 1575L),
                Map.entry("Continent", 6L),
                Map.entry("TagCla", 71L),
                Map.entry("University", 6380L),
                Map.entry("Post", 172438L),
                Map.entry("Country", 111L),
                Map.entry("Tag", 16080L),
                Map.entry("City", 1343L),
                Map.entry("Person", 1700L),
                Map.entry("Forum", 17043L)
                ),
            Map.ofEntries(
                Map.entry(Triplet.with("Forum", "containerOf", "Post"), 172438L),
                Map.entry(Triplet.with("Person", "workAt", "Company"), 3637L),
                Map.entry(Triplet.with("Comment", "replyOf", "Comment"), 108027L),
                Map.entry(Triplet.with("Person", "isLocatedIn", "City"), 1700L),
                Map.entry(Triplet.with("Post", "isLocatedIn", "Country"), 172438L),
                Map.entry(Triplet.with("Country", "isPartOf", "Continent"), 111L),
                Map.entry(Triplet.with("Comment", "hasCreator", "Person"), 215488L),
                Map.entry(Triplet.with("Person", "hasInterest", "Tag"), 39170L),
                Map.entry(Triplet.with("Post", "hasCreator", "Person"), 172438L),
                Map.entry(Triplet.with("Forum", "hasMember", "Person"), 273866L),
                Map.entry(Triplet.with("Post", "hasTag", "Tag"), 66346L),
                Map.entry(Triplet.with("Comment", "hasTag", "Tag"), 252881L),
                Map.entry(Triplet.with("Tag", "hasType", "TagCla"), 16080L),
                Map.entry(Triplet.with("University", "isLocatedIn", "City"), 6380L),
                Map.entry(Triplet.with("Forum", "hasTag", "Tag"), 54513L),
                Map.entry(Triplet.with("Comment", "isLocatedIn", "Country"), 215488L),
                Map.entry(Triplet.with("TagCla", "isSubclassOf", "TagCla"), 70L),
                Map.entry(Triplet.with("Person", "studyAt", "University"), 1374L),
                Map.entry(Triplet.with("Forum", "hasModerator", "Person"), 17043L),
                Map.entry(Triplet.with("Company", "isLocatedIn", "Country"), 1575L),
                Map.entry(Triplet.with("Person", "knows", "Person"), 18135L),
                Map.entry(Triplet.with("Person", "likes", "Post"), 70199L),
                Map.entry(Triplet.with("City", "isPartOf", "Country"), 1343L),
                Map.entry(Triplet.with("Comment", "replyOf", "Post"), 107461L),
                Map.entry(Triplet.with("Person", "likes", "Comment"), 92203L)
            ));
    }
}
