import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.Scope;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal.Admin;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.Bytecode;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalExplanation;
import org.apache.tinkerpop.gremlin.structure.Column;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.javatuples.Pair;

import graphs.GremlinGraph;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

// record QualifiedTraverser(Object Sack, )


public class NewGremlinApp {    
     public static void main(String[] args) throws Exception {
        // Connection to local graph
        GremlinGraph graph = GremlinGraph.getInstance();
        graph.setLocalGraph("g");
        GraphTraversalSource g = graph.currentGraph;
        // (a) -> (b) -> (c) 
        // (c) -> (c)

        List<Edge> edges = g.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = g.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));

        // SIMPLE Traversal
        System.out.println("Simple traversal");
        simpleTraversalDemo(g, 2);

        System.out.println("Trail traversal");
        trailTraversalDemo(g, 2);

        graph.setLocalGraph("g5");
        g = graph.currentGraph;
        // (a) -> (b) -> (c) 
        // (c) -> (c)
        // (c) -> (b)
        // (b) -> (c)

        System.out.println("Out Trail traversal");
        outTrailTraversalDemo(g, 2);

        System.out.println("Out Trail traversal 2");
        outTrailTraversal2Demo(g, 2);

        System.out.println("Out Trail traversal 3");
        outTrailTraversal3Demo(g, 2);

        System.out.println("Out Trail traversal 3 (3)");
        outTrailTraversal3Demo(g, 3);

        System.out.println("Lambda Trail traversal");
        lambdaTrailTraversalDemo(g, 2);

        System.out.println("Lambda Trail traversal (3)");
        lambdaTrailTraversalDemo(g, 3);

        graph.setLocalGraph("g");
        g = graph.currentGraph;

        if (true)
        {
            return;
        }

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

        System.out.println("Fake Match traversal");
        fakeMatchDemo(g);

        System.out.println("Fake Match traversal 2");
        fakeMatchDemo2(g);

        System.out.println("Fake Match traversal 3");
        fakeMatchDemo3(g);

        System.out.println("Where Match traversal");
        whereMatchDemo(g);

        System.out.println("Where Match traversal");
        whereMatchDemo(g);

        graph.setLocalGraph("g5");
        // (a) -> (b) -> (c) 
        // (c) -> (c)
        // (c) -> (b)
        // (b) -> (c)
        GraphTraversalSource g_cyclic = graph.currentGraph;
        System.out.println("Hybrid Graph Match traversal");
        hybridGraphMatchDemo(g_cyclic);

        System.out.println("Star iter traversal");
        starIterDemo(g_cyclic);

        System.out.println("Aggriter traversal");
        aggrIterDemo(g_cyclic);

        // System.out.println("Map tests");
        // mapTestDemo(g_cyclic);
    }



     // need: union of simple paths and paths that are simple
    //       until end and then return to start
    public static void simpleTraversalDemo(GraphTraversalSource g, int num)
    {
        assert(num > 1); // a Simple path of length 1 is just an arbitrary path

        GraphTraversal<Object,Vertex> traversal1 = V().
            repeat(both()).
            times(num).
            simplePath();

        // simplePath filter before last edge, which must return to starting vertex
        GraphTraversal<Object,Vertex> traversal2 = V().
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
        

        for (@SuppressWarnings("rawtypes") Pair<TraversalStrategy, Admin<?, ?>> x : expl.getStrategyTraversals()) {
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

    // out trail traversal
    public static void outTrailTraversalDemo(GraphTraversalSource g, int num) 
    {
        // see https://tinkerpop.apache.org/docs/current/reference/#dedup-step
        @SuppressWarnings("unchecked")
        List<Path> table = g.V().
            repeat(
                outE().
                dedup().
                inV()
            ).
            times(num).
            path().
            toList();
        table.forEach(p -> System.out.println(p.toString()));
    }

    public static void outTrailTraversal2Demo(GraphTraversalSource g, int num) 
    {
        // see https://tinkerpop.apache.org/docs/current/reference/#dedup-step
        List<Path> table = g.V().
            outE().
            as("visited").
            inV().
            outE().
            where(P.neq("visited")).
            inV().
            path().
            toList();

        table.forEach(p -> System.out.println(p.toString()));
    }

    // can we even do trail properly??
    public static void outTrailTraversal3Demo(GraphTraversalSource g, int num) 
    {
        // see https://tinkerpop.apache.org/docs/current/reference/#dedup-step
        
        // where + any doesn't seem to work 
        // seems doomed


        List<Path> table = g.V().
            outE().
            as("visited").
            inV().
            repeat(
                outE().
                as("check").
                where(not(
                    select(Pop.all, "visited").sideEffect(s -> System.out.println("v " + s)).any(P.eq("check")).
                    sideEffect(s -> System.out.println("s " + s))
                )).
                as("visited").
                inV()
            ).
            times(num-1).
            path().
            toList();

        table.forEach(p -> System.out.println(p.toString()));

        // System.out.println("unrestricted");

        // List<Path> table2 = g.V().
        //     outE().
        //     // as("visited").
        //     inV().
        //     repeat(
        //         outE().
        //         // where(P.neq("visited")).
        //         // as("visited").
        //         inV()
        //     ).
        //     times(num-1).
        //     path().
        //     toList();

        // table2.forEach(p -> System.out.println(p.toString()));
    }

    // lambda traversal
    // reasoning
    // can use VertexProgram instead of lambda to achieve same affect in OLAP
    // design lambda so as not to cause perfomance issues running in main memory
    // problems
    // hard to get sideeffect result out of sack
    // hard to use a mutating sack, must add and return this, not just add, otherwise will assign to void

    // lambda steps can use traverser.sack() for easier manipulation
    public static void lambdaTrailTraversalDemo(GraphTraversalSource g, int num)
    {
        List<Path> table = g.
        withSack(() -> new HashSet<Element>(), (s) -> new HashSet<>(s)).
        V().
        repeat(
            outE().
            filter(t -> !(((HashSet<Element>) t.sack()).contains(t.get()))).
            sideEffect(t -> ((HashSet<Element>) t.sack()).add(t.get())).
            inV()
        ).
        times(num).
        path().
        toList();

        table.forEach(r -> System.out.println(r));
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
                as("x").identity().as("x1").toE(Direction.OUT).toV(Direction.IN).as("y"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("z"),
                as("y").toE(Direction.OUT).toV(Direction.IN).as("x"),
                as("x").toE(Direction.OUT).toV(Direction.IN).as("y")
            ).
            select("x", "y", "z", "x1").
            by().by().by().by(valueMap()).
            toList();

        // GraphTraversal<Vertex, Map<String, Object>> tr = g.V().
        //     match(
        //         as("x").toE(Direction.OUT).toV(Direction.IN).as("y"),
        //         as("y").toE(Direction.OUT).toV(Direction.IN).as("z"),
        //         as("y").toE(Direction.OUT).toV(Direction.IN).as("x"),
        //         as("x").toE(Direction.OUT).toV(Direction.IN).as("y")
        //     ).
        //     select("x", "y", "z", "z");

        // works just fine
        table.forEach(p -> System.out.println(p.toString()));
    }

    // works!
    public static void fakeMatchDemo(GraphTraversalSource g) 
    {
    List<Map<String, Object>> table = g.V().
        as("x", "x1").toE(Direction.OUT).toV(Direction.IN).as("y").toE(Direction.OUT).toV(Direction.IN).as("z").
        select("y").to(Direction.OUT).where(P.eq("x")).to(Direction.OUT).where(P.eq("y")).
        select("x", "y", "z", "x1").
        by().by().by().by(valueMap()).
        toList();

    table.forEach(p -> System.out.println(p.toString()));
    }


    // Q1
    // x -> y > z
    // y -> x -> y

    // Q2
    // x -> y > z
    // a -> x
    // a -> b -> a
    // if there's a shared variable center on it and select
    // otherwise use union
    // but what if there's a path with a shared variable with two distinct universes
    // add it after the union?

    // approaches
    // aggregate and cap
    // product
    // very long traversal with skips back using select
    // this works but now we just have one long chain
    // can't use matches counting algorithm to order our traversals nicely

    // to do implement this for everything

    public static void fakeMatchDemo2(GraphTraversalSource g) 
    {
    List<Map<String, Object>> table = g.V().
        as("x").
        out().
        as("y"). 
        out(). 
        as("z"). 
        select("x").
        in(). 
        as("a"). 
        out(). 
        as("b").
        out().
        where(P.eq("a")).
        select("x", "y", "z", "a", "b").
        toList();

    table.forEach(p -> System.out.println(p.toString()));
    }

    // ACYLIC x -> y -> z
    public static void fakeMatchDemo3(GraphTraversalSource g) 
    {
    List<Map<String, Object>> table = g.V().
        as("x").
        out().
        as("y"). 
        out(). 
        as("z").
        simplePath().
        select("x", "y", "z").
        toList();

    table.forEach(p -> System.out.println(p.toString()));
    }

    public static void whereMatchDemo(GraphTraversalSource g) 
    {
        // folded down
        List<Map<String, Object>> table = g.V().
            match(
                as("x").out().as("y"),
                as("y").out().as("z")
            ).
            select("x", "y", "z").
            toList();
        
        System.out.println("Full");
        table.forEach(p -> System.out.println(p.toString()));

        List<Map<String, Object>> table2 = g.V().
        match(
            as("x").out().as("y"),
            as("y").out().as("z")
        ).
        select("x", "y", "z").
        where("z", P.neq("x").and(P.neq("y"))).
        where("y", P.neq("x").and(P.neq("z"))).
        where("x", P.neq("z").and(P.neq("y"))).
        select("x", "y", "z").
        toList();
        // P.without("x", "y")
        // without might be busted??
        System.out.println("Simple");
        table2.forEach(p -> System.out.println(p.toString()));


        List<Map<String, Object>> table3 = g.V().
        match(
            as("x").out().as("y"),    
            as("y").out().as("z")
        ).
        select("x", "y", "z").
        simplePath().from("x").to("z").
        toList();
        System.out.println("Simple 2");
        table3.forEach(p -> System.out.println(p.toString()));


        // doesn't really work
        // but using where neq does work and we can use that when applicable
        // amount of labelled singletons in a path pattern should be relatively small
        // amount of labelled singletons join
    }

    // doesn't work(?)
    // doesn't work real bad
    // public static void newMatchDemo(GraphTraversalSource g) 
    // {
    //     // List<Map<String, Object>> table = g.V().
    //     // match(
    //     //     as("x"),
    //     //     as("y"),
    //     //     as("z"),
    //     //     as("a"),
    //     //     as("b"),
    //     //     as("x").out().where(P.eq("y")).out().as("z"),
    //     //     as("a").out().as("x"),
    //     //     as("a").out().where(P.eq("b")).out().as("a")
    //     // ).
    //     // select("x", "y", "z", "a", "b").
    //     // toList();

    //     // table.forEach(p -> System.out.println(p.toString()));

    //     @SuppressWarnings("unchecked")
    //     List<Map<String, Object>> table = g.V().
    //     match(
    //         as("x").out().coalesce(as("y"), where(P.eq("y"))).out().simplePath().as("z"),
    //         as("x").out().coalesce(as("y"), where(P.eq("y"))).out().as("x")
    //     ).
    //     select("x", "y", "z").
    //     toList();

    //     table.forEach(p -> System.out.println(p.toString()));
    // }
    
    public static void hybridGraphMatchDemo(GraphTraversalSource g) 
    {
        // duplicate results for traversals along different edges!
        // is this guaranteed behaviour for match?
        List<Map<String, Object>> table = g.V().
        as("x").out().as("y").out().as("z").simplePath().
        select("z").
        match(
            as("z").out().as("a"),
            as("a").out().as("z")
        ).
        select("x", "y", "z", "a").
        toList();

        System.out.println("hybrid 1");
        table.forEach(p -> System.out.println(p.toString()));

        // double check
        List<Map<String, Object>> table2 = g.V("n3").
        match(
            as("z").out().as("a"),
            as("a").out().as("z")
        ).
        select("z", "a").
        toList();

        System.out.println("sanity check dupe results");
        table2.forEach(p -> System.out.println(p.toString()));
        // we get three results here
        // the traverser does indeed dupe along the multiple edges, but we got six in the original
        // did we already have two traversers?? why??

        List<Map<String, Object>> table3 = g.V().
        as("x").out().as("y").out().as("z").simplePath().
        select("x", "y", "z").
        toList();

        System.out.println("sanity check dupe results 2");
        table3.forEach(p -> System.out.println(p.toString()));

        // there are two simple paths! (two edges from n2 to n3)
        // (a) -[e1]-> (b) -[e2]-> (c)
        // (a) -[e1]-> (b) -[e5]-> (c)

        // there are three two length cycles starting from (c)
        // (c) -[e3]-> (c) -[e3]-> (c)
        // (c) -[e4]-> (c) -[e2]-> (c)
        // (c) -[e4]-> (c) -[e5]-> (c)

        // joining these on '(c)' (labelled z) above gives us our six results

        // the traversal works as intended!

        List<Map<String, Object>> table4 = g.V().
        as("x").out().as("y").out().as("z").simplePath().
        select("z").
        match(
            as("_!start").out().as("a"),
            as("a").out().as("_!start")
        ).
        select("x", "y", "z", "a").
        toList();

        System.out.println("sanity check match labelling");
        table4.forEach(p -> System.out.println(p.toString()));

        // the label for our join vertex in the match statement is arbitrary
    }

    public static void starIterDemo(GraphTraversalSource g) 
    {
        System.out.println("Iter Simple");
        List<Path> table1 = g.V().
            repeat(
                out()
            ).
            until(not(simplePath())).
            path().
            toList();
        table1.forEach(p -> System.out.println(p.toString()));

        System.out.println("Iter Trail");
        
        List<Path> table2 = g.V().
            repeat(
                outE().
                dedup().
                otherV()
            ).
            emit().
            // barrier(). // barrier shouldn't be needed because all the variables set in an iteration are group rather than singleton
            // they can't be joined or conditioned on in the rest of the pattern, so can be evaluated lazily
            path().
            toList();
        table2.forEach(p -> System.out.println(p.toString()));

    }

    // seems to work -- NOT
    public static void aggrIterDemo(GraphTraversalSource g) 
    {
        // graph
        // (a) -> (b) -> (c) 
        // (c) -> (c)
        // (c) -> (b)
        // (b) -> (c)

        // pattern
        // (x) -> [(y) -> (z)]{1,3}

        // result (wrong this is concatenative and not right there either)
        // (a) -> (b:y) -1> (c: z)
        // (a) -> (b:y) -2> (c: z)
        // (a) -> (b:y) -1> (c: z) -> (c:y) -> (c: z)
        // (a) -> (b:y) -2> (c: z) -> (c:y) -> (c: z)
        // (a) -> (b:y) -1> (c: z) -> (c:y) -> (c: z) -> (c:y) -> (c: z)
        // (a) -> (b:y) -2> (c: z) -> (c:y) -> (c: z) -> (c:y) -> (c: z)
        // (a) -> (b:y) -1> (c: z) -> (b:y) -1> (c:z)
        // (a) -> (b:y) -1> (c: z) -> (b:y) -2> (c:z) 
        // (a) -> (b:y) -2> (c: z) -> (b:y) -1> (c:z)
        // (a) -> (b:y) -2> (c: z) -> (b:y) -2> (c:z) 

        // pattern
        // (x) -> [(y) -> (z)]{1,2}

        // result
        // (a) -> (b:y) -1> (c: z)
        // (a) -> (b:y) -2> (c: z)
        // (a) -> (b:y) -1> (c: z) -> (c:y)
        // (a) -> (b:y) -2> (c: z) -> (c:y)
        // (a) -> (b:y) -1> (c: z) -> (b:y)
        // (a) -> (b:y) -2> (c: z) -> (b:y)

        System.out.println("Aggr Iter");
        
        // WORKS
        List<Map<String,Object>> table1 = g.V("n1").
            as("x").
            sideEffect(e -> System.out.println("a: " + e)).
            outE(). 
            as("e1").
            otherV().
            repeat(
                as("y").
                outE(). 
                as("e2").
                otherV().
                as("z")
            ).
            times(2).
            emit(loops().is(P.gt(0))).
            select(Pop.all,"x", "e1", "y", "e2", "z").
            toList();
        table1.forEach(p -> System.out.println(p.toString()));

        System.out.println("Union join");
        @SuppressWarnings("unchecked")
        List<?> table2 = g.V("n1").
            as("x").
            sideEffect(e -> System.out.println("a: " + e)).
            outE(). 
            as("e1").
            otherV().
            repeat(
                as("y").
                outE(). 
                as("e2").
                otherV().
                as("z")
            ).
            times(2).
            emit(loops().is(P.gt(0))).
            local(
                union(
                    select(Pop.last,"x").project("x"),
                    select(Pop.all, "e1", "y", "e2", "z")
                ).
                unfold(). 
                group(). 
                by(select(Column.keys)).
                by(select(Column.values))
            ).
            toList();
        table2.forEach(p -> System.out.println(p.toString()));

    // can use union and unfold to merge selection of singleton variables with Pop.single and group variables with Pop.all
    }


    // public static void mapTestDemo(GraphTraversalSource g) 
    // {
    //     // graph
    //     // (a) -> (b) -> (c) 
    //     // (c) -> (c)
    //     // (c) -> (b)
    //     // (b) -> (c)
    //     System.out.println("m1");
    //     @SuppressWarnings("unchecked")
    //     List<?> table = g.
    //         inject(0).
    //         project("x").
    //         inject(Map.of("test", 123)).
    //         unfold().
    //         toList();
    //     table.forEach(r -> System.out.println(r + " type = " + r.getClass()));

    //     System.out.println("m2");
    //     // @SuppressWarnings("unchecked")
    //     List<?> table2 =  g.V().map(
    //         elementMap().
    //         unfold().
    //         group().
    //         by(select(Column.keys).asString()).
    //         by(select(Column.values))
    //     ).toList();
    //     table2.forEach(r -> System.out.println(r + " type = " + r.getClass()));

    //     System.out.println("m3");
    //     @SuppressWarnings("unchecked")
    //     GraphTraversal<?, Map<String, Object>> t = (GraphTraversal<?, Map<String, Object>>) (Object) 
    //         elementMap().
    //         unfold().
    //         group().
    //         by(select(Column.keys).asString()). // key values are strings
    //         by(select(Column.values)); // value values are objects
    //         // compiler isn't aware of this, we cast unchecked to type correctly

    //     @SuppressWarnings("unchecked")
    //     List<?> table3 = g.V().map(
    //         union(
    //             inject(0).
    //             project("x"),
    //             t
    //         ).
    //         unfold().
    //         group().
    //         by(select(Column.keys).asString()). // key values are strings
    //         by(select(Column.values)) // value values are objects
    //     ).toList();
    //     table3.forEach(r -> System.out.println(r + " type = " + r.getClass()));


    //     @SuppressWarnings("unchecked")
    //     TraversalExplanation e3 = g.V().map(
    //         union(
    //             inject(0).
    //             project("x"),
    //             (GraphTraversal<?, Map<String, Object>>) (Object) 
    //             elementMap().
    //             unfold().
    //             group().
    //             by(select(Column.keys).asString()). // key values are strings
    //             by(select(Column.values))
    //         ).
    //         unfold().
    //         group().
    //         by(select(Column.keys).asString()). // key values are strings
    //         by(select(Column.values))
    //     ).explain(); // value values are objects
    //     System.out.println(e3.prettyPrint());
        
    // }
    
}
