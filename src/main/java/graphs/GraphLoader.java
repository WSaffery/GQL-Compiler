package graphs;

import java.util.ArrayList;
import java.util.stream.Stream;

import exceptions.InvalidEdgeFormatException;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;

public interface GraphLoader {
    public abstract void loadJsonGraph(ArrayList<JsonNode> nodes, ArrayList<JsonEdge> edges) throws InvalidEdgeFormatException;

    public abstract void streamJsonGraph(Stream<JsonNode> nodes, Stream<JsonEdge> edges) throws InvalidEdgeFormatException;
}
