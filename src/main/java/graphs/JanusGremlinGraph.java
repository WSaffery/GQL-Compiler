package graphs;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.V;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import exceptions.InvalidEdgeFormatException;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;

public class JanusGremlinGraph implements GraphLoader {
    public GraphTraversalSource currentGraph;
    public Map<String, Object> idMap;
    private ArrayList<JsonNode> nodeBuffer;
    private ArrayList<JsonEdge> edgeBuffer;
    private int FLUSH_SIZE = 100;

    public JanusGremlinGraph(GraphTraversalSource gts) {
        this.currentGraph = gts;
        this.idMap = new HashMap<>();
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
        // Transaction tr = currentGraph.tx();
        // tr.open();
        // tr.begin();
        nodes.forEach(this::addNodeToCurrentGraphBuffered);
        flushNodeBuffer();

        // assert(tr.isOpen());
        // System.out.println(currentGraph.V().id().toList());
        // for (Object id : idMap.values())
        // {
        //     System.out.println(id);
        //     System.out.println(currentGraph.V(id).id().toList());
        // }

        edges.forEach(this::addEdgeToCurrentGraphBuffered);
        flushEdgeBuffer();
        // tr.close();

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

    private void addNodesToCurrentGraph(ArrayList<JsonNode> nodes) {

        GraphTraversal<Vertex, Vertex> pipe = null;

        for (JsonNode node : nodes)
        {
            // custom vertex ids are dropped and then locally mapped to the generated id
        
            if (node.labels != null)
                assert(node.labels.size() >= 1);

            if (pipe == null)
            {
                pipe = node.labels != null ?
                    this.currentGraph.addV(node.labels.get(0)) : 
                    this.currentGraph.addV();
            }
            else 
            {
                pipe = node.labels != null ?
                    pipe.addV(node.labels.get(0)) : 
                    pipe.addV();
            }
            
            // should in theory speed up janus if we 
            // add a index on the "label" property
            // if (node.labels != null) {
            //     pipe.property("label", node.labels.get(0));
            // }


            if (!(node.properties == null)) {
                Map<String, Object> properties = node.properties;
                for (Map.Entry<String, Object> e : properties.entrySet())
                {
                    String propertyId = e.getKey();
                    Object value = e.getValue();
                    pipe = pipe.property(propertyId, value);
                }
            }

            pipe = pipe.as("v");
        }

        // System.out.println("Hi");
        // System.out.println(pipe);

        // add final steps to traversal (select all added vertices and grab their ids)
        // also get result with toList
        List<Object> actualIds = pipe.select(Pop.all, "v").unfold().id().toList();

        assert(actualIds.size() == nodes.size());
        // map dropped custom ids
        for (int i = 0; i < actualIds.size(); i++)
        {
            String jsonId = nodes.get(i).identity;
            Object actualId = actualIds.get(i);
            idMap.put(jsonId, actualId);
        }
    }

    private void addNodeToCurrentGraph(JsonNode node) {
        String jsonId = node.identity;
        // custom vertex ids are dropped and then locally mapped to the generated id
        
        if (node.labels != null)
            assert(node.labels.size() >= 1);

        GraphTraversal<Vertex, Vertex> pipe = node.labels != null ?
            this.currentGraph.addV(node.labels.get(0)) : 
            this.currentGraph.addV();

        if (!(node.labels == null)) {
            // pipe.property("labels", node.labels.clone());
        }

        if (!(node.properties == null)) {
            Map<String, Object> properties = node.properties;
            properties.forEach((propertyId, value) -> {
                pipe.property(propertyId.toString(), value);
            });
        }

        Object actualId = pipe.id().next();
        
        idMap.put(jsonId, actualId);
    }

    private boolean nodeDoesNotExist(Object id) {
        return !this.currentGraph.V(id).hasNext();
    }

    private boolean nodeDoesNotExist(String id) {
        return nodeDoesNotExist(idMap.get(id));
    }

    private void checkIfEdgeIsConnected(JsonEdge edge) throws InvalidEdgeFormatException {
        if (nodeDoesNotExist(edge.start)) {
            throw new InvalidEdgeFormatException("Edge " + edge.identity + " start node does not exist.");
        } else if (nodeDoesNotExist(edge.end)) {
            throw new InvalidEdgeFormatException("Edge " + edge.identity + " start node does not exist.");
        }
    }

    private void addEdgesToCurrentGraph(ArrayList<JsonEdge> edges) {
        // String id = edge.identity;
        // custom edge ids are just dropped 
        GraphTraversal<Edge, Edge> pipe = null;

        for (JsonEdge edge : edges)
        {
            if (edge.labels != null)
            assert(edge.labels.size() >= 1);

            if (pipe == null)
            {
                pipe = edge.labels != null ?
                    this.currentGraph.addE(edge.labels.get(0)) :
                    this.currentGraph.addE("nolabel");
            }
            else 
            {
                pipe = edge.labels != null ?
                    pipe.addE(edge.labels.get(0)) :
                    pipe.addE("nolabel");
            }
            

            Object sourceNodeId = idMap.get(edge.start);
            Object targetNodeId = idMap.get(edge.end);

            pipe.from(V(sourceNodeId)).to(V(targetNodeId));

            if (!(edge.labels == null)) {
                // pipe.property("labels", edge.labels.clone());
            }

            if (!(edge.properties == null)) {
                Map<String, Object> properties = edge.properties;
                for (Map.Entry<String, Object> e : properties.entrySet())
                {
                    String propertyId = e.getKey();
                    Object value = e.getValue();
                    pipe.property(propertyId, value);
                }
            }

            pipe.property("isDirected", edge.isDirected);
        }

        pipe.iterate();
    }

    private void addEdgeToCurrentGraph(JsonEdge edge) {
        // String id = edge.identity;
        // custom edge ids are just dropped 

        if (edge.labels != null)
            assert(edge.labels.size() >= 1);

        GraphTraversal<Edge, Edge> pipe = edge.labels != null ?
            this.currentGraph.addE(edge.labels.get(0)) :
            this.currentGraph.addE("nolabel");

        Object sourceNodeId = idMap.get(edge.start);
        Object targetNodeId = idMap.get(edge.end);

        pipe.from(V(sourceNodeId)).to(V(targetNodeId));

        if (!(edge.labels == null)) {
            // pipe.property("labels", edge.labels.clone());
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
