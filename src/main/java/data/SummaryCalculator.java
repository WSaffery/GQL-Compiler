package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Column;
import org.javatuples.Triplet;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

public class SummaryCalculator {

    public static List<String> toStrings(@SuppressWarnings("rawtypes") Iterable objects)
    {
        List<String> strings = new ArrayList<>();
        for (Object o : objects)
        {
            assert o instanceof String : "Bad triplet cardinality result";
            strings.add((String) o);
        }
        return strings;
    }
    
    public static Summary calculateSummary(GraphTraversalSource gts)
    {
        Map<String, Long> nodeCardinalities = new HashMap<>();
        Map<Triplet<String, String, String>, Long> tripletCardinalities = new HashMap<>();

        List<Map<Object,Long>> nodeResult = gts.V().groupCount().by(label()).toList();
        assert nodeResult.size() == 1 : "More results than expected";
        
        for (Entry<Object, Long> count : nodeResult.get(0).entrySet())
        {
            assert count.getKey() instanceof String : "Bad node cardinality result";
            nodeCardinalities.put((String) count.getKey(), count.getValue());
        }

        List<Map<Object,Long>> tripletResult = gts.V().as("u").
            outE().as("e").
            inV().as("v").
            groupCount().
            by(
                select("u","e","v").
                by(label()).
                select(Column.values)
            ).toList();
        assert tripletResult.size() == 1 : "More results than expected";

        for (Entry<Object, Long> count : tripletResult.get(0).entrySet())
        {
            assert count.getKey() instanceof Iterable : "Bad triplet cardinality result";
            
            @SuppressWarnings("rawtypes")
            Triplet<String, String, String> key = Triplet.fromCollection(toStrings((Iterable) count.getKey()));
            
            tripletCardinalities.put(key, count.getValue());
        }
        

        return new Summary(nodeCardinalities, tripletCardinalities);
    }
}
