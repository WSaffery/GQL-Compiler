import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal.Admin;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.Bytecode;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.DefaultGraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalExplanation;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.javatuples.Pair;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

// import gql.graphs.Graph;
import gql.graphs.GremlinGraph;


public class NewGremlinApp {    
     public static void main(String[] args) throws Exception {
        // Connection to local graph
        GremlinGraph graph = GremlinGraph.getInstance();
        graph.setLocalGraph("g");
        GraphTraversalSource g = graph.currentGraph;

        List<Edge> edges = g.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = g.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));

        // SIMPLE Traversal
        System.out.println("Simple traversal");
        simpleTraversalDemo(g, 2);

        System.out.println("Trail traversal");
        trailTraversalDemo(g, 2);

        System.out.println("Inward traversal");
        directedTraversalDemo(g, Direction.IN, 2);

        System.out.println("Outward traversal");
        directedTraversalDemo(g, Direction.OUT, 2);

        // g.addE("e4").from(V("n2")).to(V("n1")).property("test", 123).property("test2", 1234).iterate();

        // List<Map<Object,Object>> edges2 = g.E().hasLabel("e4").valueMap().toList();
        // edges2.forEach(e -> System.out.println(e.toString()));


        System.out.println("Match traversal");
        matchDemo(g);

        System.out.println("Match traversal 2");
        matchDemo2(g);
    }



     // need: union of simple paths and paths that are simple
    //       until end and then return to start
    public static void simpleTraversalDemo(GraphTraversalSource g, int num)
    {
        assert(num > 1); // a Simple path of length 1 is just an arbitrary path

        GraphTraversal<Vertex,Vertex> traversal1 = g.V().
            repeat(both()).
            times(num).
            simplePath();

        // simplePath filter before last edge, which must return to starting vertex
        GraphTraversal<Vertex,Vertex> traversal2 = g.V().
            as("a").
            repeat(both()).
            times(num-1).
            simplePath().
            both().
            where(P.eq("a"));

        @SuppressWarnings("unchecked")
        GraphTraversal<Vertex,Path> fullTraversal = g.V().union(traversal1, traversal2).path();

        TraversalExplanation expl = fullTraversal.explain();
        
        List<Path> table = fullTraversal.toList();

        table.forEach(p -> System.out.println(p.toString()));

        HashMap<Bytecode, ArrayList<String>> bytecodes = new HashMap<>();
        
        for (Pair<TraversalStrategy, Admin<?, ?>> x : expl.getStrategyTraversals()) {
            Bytecode bytecode = x.getValue1().getBytecode();
            if (bytecodes.containsKey(bytecode)) {
                bytecodes.get(bytecode).add(x.getValue0().toString());
            }
            else {
                ArrayList<String> strats = new ArrayList<>();
                bytecodes.put(bytecode, strats);
                strats.add(x.getValue0().toString());
            }
        }

        System.out.println("Original Byte Code: ");
        System.out.println(expl.getOriginalTraversal().getBytecode());

        for (Map.Entry<Bytecode, ArrayList<String>> x : bytecodes.entrySet())
        {
            System.out.println("Used by: " + String.join(" AND ", x.getValue()));
            System.out.println(x.getKey());
        }
        // System.out.printf("Traversal Explanation%n%s%n", expl.prettyPrint());

    }

    // trail traversal
    public static void trailTraversalDemo(GraphTraversalSource g, int num) 
    {
        // see https://tinkerpop.apache.org/docs/current/reference/#dedup-step
        @SuppressWarnings("unchecked")
        List<Path> table = g.V().
            repeat(
                bothE().
                dedup().
                coalesce(otherV(), inV(), outV())
            ).
            times(num).
            path().
            toList();
        table.forEach(p -> System.out.println(p.toString()));

        System.out.println("Table 2");

        @SuppressWarnings("unchecked")
        List<Path> table2 = g.V().
            repeat(
                bothE().
                dedup().
                coalesce(otherV(), inV(), outV())
            ).
            times(num).
            path().
            toList();
        table2.forEach(p -> System.out.println(p.toString()));
    }

    // a drop in replacement for toE that will continue along "undirected" edges pointing in the "wrong" direction.
    @SuppressWarnings("unchecked")
    public static GraphTraversal<Vertex, Edge> alongE(final Direction dir, final String... edgeLabels)
    {
        if (dir == Direction.BOTH) return bothE();
        return coalesce(toE(dir.opposite(), edgeLabels).has("isDirected", 0),
                        toE(dir, edgeLabels));
    }

    // directed traversal
    // will return all paths travelling num hops in given direction dir
        // will continue along "undirected" edges pointing in the wrong direction
    public static void directedTraversalDemo(GraphTraversalSource g, Direction dir, int num) 
    {
        List<Path> table = g.V().
            repeat(
                alongE(dir).
                otherV()
            ).
            times(num).
            path().
            toList();
        table.forEach(p -> System.out.println(p.toString()));

    }


    public static void matchDemo(GraphTraversalSource g) 
    {
    
    // not quite
    List<Map<String, Object>> table = g.V().
            match(
                as("x").toE(Direction.OUT).toV(Direction.IN).as("y").toE(Direction.OUT).toV(Direction.IN).as("z"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("x").toE(Direction.OUT).toV(Direction.IN).as("y")
            ).
            select("x", "y", "z").
            toList();

    // "The provided match pattern is unsolvable"
    // List<Map<String, Object>> table = g.V().
    //         match(
    //             as("x").toE(Direction.OUT).toV(Direction.IN).as("y1").toE(Direction.OUT).toV(Direction.IN).as("z"),
    //             as("y2").toE(Direction.OUT).toV(Direction.IN).as("x").toE(Direction.OUT).toV(Direction.IN).as("y2")
    //         ).
    //         where("y1", P.eq("y2")).
    //         select("x", "y2", "z").
    //         toList();
        table.forEach(p -> System.out.println(p.toString()));
    }

    public static void matchDemo2(GraphTraversalSource g) 
    {
        // folded down
        List<Map<String, Object>> table = g.V().
            match(
                as("x").toE(Direction.OUT).toV(Direction.IN).as("y"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("z"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("x"),
                as("x").toE(Direction.OUT).toV(Direction.IN).as("y")
            ).
            select("x", "y", "z").
            toList();

        GraphTraversal<Vertex, Map<String, Object>> tr = g.V().
            match(
                as("x").toE(Direction.OUT).toV(Direction.IN).as("y"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("z"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("x"),
                as("x").toE(Direction.OUT).toV(Direction.IN).as("y")
            ).
            select("x", "y", "z");

        // works just fine
        table.forEach(p -> System.out.println(p.toString()));
    }
}
