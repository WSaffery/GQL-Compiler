package cli.display;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.tinkerpop.gremlin.process.traversal.util.Metrics;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalMetrics;

import ast.GqlProgram;

public class DisplayHelpers {
    public static void printProgram(GqlProgram program)
    {
        program.body.getQuery(0).print();
        for (int i = 1; i < program.body.getQueryCount(); i++)
        {
            System.out.println(program.body.getConjunctor(i-1));
            program.body.getQuery(i).print();
        }
    }

    public static void printTable(List<Map<String,Object>> maps, PrintStream printStream)
    {   
        Map<String, Integer> sizes = new HashMap<>();
        for (Map<String, Object> map : maps) 
        {
            for (Map.Entry<String, Object> e : map.entrySet())
            {
                String key = e.getKey();
                Integer len = e.getValue().toString().length();

                // default length is key length
                sizes.putIfAbsent(key, key.length()); 
                sizes.compute(key, (s, v) -> Integer.max(v, len));
            }
        }

        for (Map.Entry<String, Integer> e : sizes.entrySet())
        {
            // System.out.println(e);
            // String width = Integer.toString(e.getValue());
            String format = " %%-%ds|".formatted(e.getValue());
            // get "%-[width]s"

            printStream.printf(format, e.getKey());
        }
        printStream.println();

        for (Map<String, Object> map : maps)
        {
            for (String key : sizes.keySet())
            {
                Integer size = sizes.get(key);
                String format = " %%-%ds|".formatted(size);
                if (map.containsKey(key))
                {
                    printStream.printf(format, map.get(key));
                }
                else 
                {
                    printStream.printf(format, "NO MATCH");
                }
            }
            printStream.println();
        }
    }

    public static void printMetrics(TraversalMetrics metrics, PrintStream printStream)
    {
        List<Map<String,Object>> table = new ArrayList<>();
        for (Metrics metric : metrics.getMetrics())
        {
            String name = metric.getName();
            Long count = metric.getCount(TraversalMetrics.ELEMENT_COUNT_ID);
            Long traversers = metric.getCount(TraversalMetrics.TRAVERSER_COUNT_ID);
            Object durationPercAnnotation = metric.getAnnotation(TraversalMetrics.PERCENT_DURATION_KEY);
            long duration = metric.getDuration(TimeUnit.MILLISECONDS);
            table.add(Map.of(
                "Name", name,
                "Count", count, 
                "Traversers", traversers, 
                "Duration (ms)", duration,
                "Duration (%)", durationPercAnnotation
            ));
        }
        printTable(table, printStream);
    }
}
