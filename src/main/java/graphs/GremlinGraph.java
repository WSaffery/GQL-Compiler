package graphs;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.V;

import java.util.ArrayList;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import exceptions.InvalidEdgeFormatException;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class GremlinGraph {
    public GraphTraversalSource currentGraph;

    protected GremlinGraph(Graph graph) {
        this.currentGraph = traversal().withEmbedded(graph);
    }

    public static GremlinGraph makGremlinGraph(
        Graph tinkerGraph, ArrayList<JsonNode> nodes, ArrayList<JsonEdge> edges) throws InvalidEdgeFormatException
    {
        GremlinGraph graph = new GremlinGraph(tinkerGraph);
        nodes.forEach(graph::addNodeToCurrentGraph);
        edges.forEach(graph::addEdgeToCurrentGraph);

        for (JsonEdge edge: edges)
        {
            graph.checkIfEdgeIsConnected(edge);
        }

        return graph;
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
