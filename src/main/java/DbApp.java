import java.io.IOException;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import exceptions.InvalidEdgeFormatException;
import exceptions.InvalidNodeFormatException;
import graphs.GremlinGraph;
import graphs.GremlinGraphFactory;
import graphs.ResourcePaths;

public class DbApp {
    public static void main(String[] args) throws InvalidNodeFormatException, InvalidEdgeFormatException, IOException {
        for (String arg : args)
        {
            System.out.println();
            testGraph(arg);
        }    
    }

    static void testGraph(String name) throws InvalidNodeFormatException, InvalidEdgeFormatException, IOException 
    {
        System.out.println("Loading graph: " + name);

        GremlinGraphFactory factory = new GremlinGraphFactory(ResourcePaths.getGraphFolder());
        GremlinGraph graph = factory.makeGremlinGraph(name);
        GraphTraversalSource g = graph.currentGraph;

        List<Edge> edges = g.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = g.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));
    }
}
