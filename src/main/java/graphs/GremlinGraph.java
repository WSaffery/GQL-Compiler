package graphs;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.V;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import exceptions.InvalidEdgeFormatException;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;

public class GremlinGraph implements GraphLoader {
    public GraphTraversalSource currentGraph;
    private ArrayList<JsonNode> nodeBuffer;
    private ArrayList<JsonEdge> edgeBuffer;
    private int FLUSH_SIZE = 100;

    public GremlinGraph(GraphTraversalSource gts) {
        this.currentGraph = gts;
        nodeBuffer = new ArrayList<>();
        edgeBuffer = new ArrayList<>();
    }

    public void loadJsonGraph(ArrayList<JsonNode> nodes, ArrayList<JsonEdge> edges)
        throws InvalidEdgeFormatException
    {
        nodes.forEach(this::addNodeToCurrentGraph);
        edges.forEach(this::addEdgeToCurrentGraph);

        for (JsonEdge edge: edges)
        {
            this.checkIfEdgeIsConnected(edge);
        }
    }

    public void streamJsonGraph(Stream<JsonNode> nodes, Stream<JsonEdge> edges) throws InvalidEdgeFormatException
    {
        nodes.forEach(this::addNodeToCurrentGraphBuffered);
        flushNodeBuffer();
        edges.forEach(this::addEdgeToCurrentGraphBuffered);
        flushEdgeBuffer();
        
        // disabled connectivity checks, can't reuse the same stream twice
        // need to use a stream supplier if I want to do this
        // nodeSupplier = () -> (...).getNodeStream();
        // edgeSupplier = () -> (...).getEdgeStream();
        // Optional<JsonEdge> startlessEdge = edges.filter(edge -> nodeDoesNotExist(edge.start)).findAny();
        // if (startlessEdge.isPresent())
        // {
        //     throw new InvalidEdgeFormatException("Edge " + startlessEdge.get().identity + " start node does not exist.");
        // }
        
        // Optional<JsonEdge> endlessEdge = edges.filter(edge -> nodeDoesNotExist(edge.end)).findAny();
        // if (endlessEdge.isPresent())
        // {
        //     throw new InvalidEdgeFormatException("Edge " + endlessEdge.get().identity + " end node does not exist.");
        // }
    }

    private void addNodeToCurrentGraphBuffered(JsonNode node) {
        nodeBuffer.add(node);
        if (nodeBuffer.size() >= FLUSH_SIZE)
        {
            flushNodeBuffer();
        }
    }

    private void addEdgeToCurrentGraphBuffered(JsonEdge edge) {
        edgeBuffer.add(edge);
        if (edgeBuffer.size() >= FLUSH_SIZE)
        {
            flushEdgeBuffer();
        }
    }

    private void flushNodeBuffer()
    {
        addNodesToCurrentGraph(nodeBuffer);
        nodeBuffer.clear();
    }

    private void flushEdgeBuffer()
    {
        addEdgesToCurrentGraph(edgeBuffer);
        edgeBuffer.clear();
    }

    private void addNodesToCurrentGraph(ArrayList<JsonNode> nodes)
    {
        GraphTraversal<Vertex, Vertex> pipe = null;
        for (JsonNode node : nodes)
        {
            String id = node.identity;

            if (node.labels != null)
                assert(node.labels.size() >= 1);
            
            if (pipe == null)
            {
                pipe = node.labels != null ?
                    this.currentGraph.addV(node.labels.get(0)).property(T.id, id) : 
                    this.currentGraph.addV().property(T.id, id);
            }
            else 
            {
                pipe = node.labels != null ?
                    pipe.addV(node.labels.get(0)).property(T.id, id) : 
                    pipe.addV().property(T.id, id);
            }

            if (!(node.labels == null)) {
                pipe.property("labels", node.labels.clone());
            }

            if (!(node.properties == null)) {
                Map<String, Object> properties = node.properties;
                for (Map.Entry<String, Object> e : properties.entrySet())
                {
                    String propertyId = e.getKey();
                    Object value = e.getValue();
                    pipe.property(propertyId, value);
                }
            }
        }

        pipe.iterate();
    }

    private void addNodeToCurrentGraph(JsonNode node) {
        String id = node.identity;
        
        if (node.labels != null)
            assert(node.labels.size() >= 1);

        GraphTraversal<Vertex, Vertex> pipe = node.labels != null ?
            this.currentGraph.addV(node.labels.get(0)).property(T.id, id) : 
            this.currentGraph.addV().property(T.id, id);

        if (!(node.labels == null)) {
            pipe.property("labels", node.labels.clone());
        }

        if (!(node.properties == null)) {
            Map<String, Object> properties = node.properties;
            properties.forEach((propertyId, value) -> {
                pipe.property(propertyId.toString(), value);
            });
        }

        pipe.iterate();
    }
    
    private void addEdgesToCurrentGraph(ArrayList<JsonEdge> edges) {
        GraphTraversal<Edge, Edge> pipe = null;

        for (JsonEdge edge : edges)
        {
            String id = edge.identity;

            if (edge.labels != null)
                assert(edge.labels.size() >= 1);

            if (pipe == null)
            {
                pipe = edge.labels != null ?
                    this.currentGraph.addE(edge.labels.get(0)).property(T.id, id) :
                    this.currentGraph.addE("edge").property(T.id, id);
            }
            else 
            {
                pipe = edge.labels != null ?
                    pipe.addE(edge.labels.get(0)).property(T.id, id) :
                    pipe.addE("edge").property(T.id, id);
            }
            

            String sourceNodeId = edge.start;
            String targetNodeId = edge.end;
            pipe.from(V(sourceNodeId)).to(V(targetNodeId));

            if (!(edge.labels == null)) {
                pipe.property("labels", edge.labels.clone());
            }

            if (!(edge.properties == null)) {
                Map<String, Object> properties = edge.properties;
                for (Map.Entry<String, Object> e : properties.entrySet())
                {
                    pipe.property(e.getKey().toString(), e.getValue());
                }
            }

            pipe.property("isDirected", edge.isDirected);
        }

        pipe.iterate();
    }

    private boolean nodeDoesNotExist(String start) {
        return !this.currentGraph.V(start).hasNext();
    }

    private void checkIfEdgeIsConnected(JsonEdge edge) throws InvalidEdgeFormatException {
        if (nodeDoesNotExist(edge.start)) {
            throw new InvalidEdgeFormatException("Edge " + edge.identity + " start node does not exist.");
        } else if (nodeDoesNotExist(edge.end)) {
            throw new InvalidEdgeFormatException("Edge " + edge.identity + " start node does not exist.");
        }
    }

    private void addEdgeToCurrentGraph(JsonEdge edge) {
        String id = edge.identity;

        if (edge.labels != null)
            assert(edge.labels.size() >= 1);

        GraphTraversal<Edge, Edge> pipe = edge.labels != null ?
            this.currentGraph.addE(edge.labels.get(0)).property(T.id, id) :
            this.currentGraph.addE("edge").property(T.id, id);

        String sourceNodeId = edge.start;
        String targetNodeId = edge.end;
        pipe.from(V(sourceNodeId)).to(V(targetNodeId));

        if (!(edge.labels == null)) {
            pipe.property("labels", edge.labels.clone());
        }

        if (!(edge.properties == null)) {
            Map<String, Object> properties = edge.properties;
            properties.forEach((propertyId, value) -> {
                pipe.property(propertyId.toString(), value);
            });
        }

        pipe.property("isDirected", edge.isDirected);

        pipe.iterate();
    }
}
