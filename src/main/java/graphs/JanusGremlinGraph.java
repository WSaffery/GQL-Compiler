package graphs;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.V;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import exceptions.InvalidEdgeFormatException;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;

public class JanusGremlinGraph implements GraphLoader {
    public GraphTraversalSource currentGraph;
    public Map<String, Object> idMap;

    public JanusGremlinGraph(GraphTraversalSource gts) {
        this.currentGraph = gts;
        this.idMap = new HashMap<>();
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
