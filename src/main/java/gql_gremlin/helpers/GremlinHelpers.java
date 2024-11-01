package gql_gremlin.helpers;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;

import java.util.Set;

import org.apache.tinkerpop.gremlin.jsr223.JavaTranslator;
import org.apache.tinkerpop.gremlin.process.traversal.Bytecode;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import ast.queries.QueryConjunctor;

public class GremlinHelpers {
    
    public static Direction toGremlinDirection(enums.Direction dir)
    {
        assert(dir != enums.Direction.UNDIRECTED);
        
        return dir == enums.Direction.LEFT_TO_RIGHT ? 
            Direction.OUT : 
            Direction.IN;
    }
    
    @SuppressWarnings("unchecked")
    public static GraphTraversal<Vertex, Edge> alongE(final enums.Direction dir, final String... edgeLabels)
    {
        // undirected -> either direction, must be undirected
        if (dir == enums.Direction.UNDIRECTED) return bothE().has("isDirected", 0);

        Direction gDir = dir == enums.Direction.LEFT_TO_RIGHT ? 
            Direction.OUT : 
            Direction.IN;

        // directed -> either same direction or opposite but undirected edge
        return coalesce(toE(gDir.opposite(), edgeLabels).has("isDirected", 0),
                        toE(gDir, edgeLabels));
    }

    public static <S, E> GraphTraversal<S, E> appendTraversal(GraphTraversalSource source, Bytecode bytecode)
    {
        JavaTranslator<?, GraphTraversal.Admin<S, E>> translator = 
            JavaTranslator.of(source);

        return translator.translate(bytecode);
    }

    @SuppressWarnings("unchecked")
    public static <A, B> GraphTraversal<A, B> conjoinTraversals(GraphTraversal<A, B> x, QueryConjunctor conjunctor, GraphTraversal<A, B> y)
    {
        // TODO: why do we even have this, remove??
        GraphTraversal<A, A> traversal = start();
        switch (conjunctor)
        {
            case UNION_ALL:
                return traversal.union(x, y);
            case UNION_DISTINCT:
                return traversal.union(x, y).dedup();
            default:
                System.out.println("Only union all and union distinct is currently supported");
                assert(false); // nice, TODO: fix
        }
        return null;
    }

    public static GraphTraversal<Vertex, Vertex> vertexStart()
    {
        return start();
    }

    public static GraphTraversal<Edge, Edge> edgeStart()
    {
        return start();
    }

    public static <T> boolean intersects(Set<T> a, Set<T> b)
    {
        Set<T> c = a.size() < b.size() ? a : b;
        for (T e : c) 
        {
            if (b.contains(e))
            {
                return true;
            }
        }
        return false;
    }
}
