package cli.display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void printTable(List<Map<String,Object>> maps)
    {   
        Map<String, Integer> sizes = new HashMap<>();
        for (Map<String, Object> map : maps) 
        {
            for (Map.Entry<String, Object> e : map.entrySet())
            {
                sizes.putIfAbsent(e.getKey(), 0);
                Integer len = e.getValue().toString().length();
                sizes.compute(e.getKey(), (s, v) -> Integer.max(v, len));
            }
        }

        for (Map.Entry<String, Integer> e : sizes.entrySet())
        {
            // System.out.println(e);
            // String width = Integer.toString(e.getValue());
            String format = " %%-%ds|".formatted(e.getValue());
            // get "%-[width]s"

            System.out.printf(format, e.getKey());
        }
        System.out.println();

        for (Map<String, Object> map : maps)
        {
            for (String key : sizes.keySet())
            {
                Integer size = sizes.get(key);
                String format = " %%-%ds|".formatted(size);
                if (map.containsKey(key))
                {
                    System.out.printf(format, map.get(key));
                }
                else 
                {
                    System.out.printf(format, "NO MATCH");
                }
            }
            System.out.println();
        }

    }
}
