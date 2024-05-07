import java.io.FileNotFoundException;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import exceptions.InvalidEdgeFormatException;
import exceptions.InvalidNodeFormatException;
import graphs.GremlinGraph;

public class DbApp {
    public static void main(String[] args) throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException {
        for (String arg : args)
        {
            System.out.println();
            testGraph(arg);
        }    
    }

    static void testGraph(String name) throws FileNotFoundException, InvalidNodeFormatException, InvalidEdgeFormatException 
    {
        System.out.println("Loading graph: " + name);

        GremlinGraph graph = GremlinGraph.getInstance();
        graph.setLocalGraph(name);
        GraphTraversalSource g = graph.currentGraph;
        List<Edge> edges = g.E().toList();
        edges.forEach(e -> System.out.println(e.toString()));
        List<Vertex> vertexs = g.V().toList();
        vertexs.forEach(v -> System.out.println(v.toString()));
    }
}
