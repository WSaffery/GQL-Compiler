package graphs;

import java.util.ArrayList;

import exceptions.InvalidEdgeFormatException;
import json.gremlin.JsonEdge;
import json.gremlin.JsonNode;

public interface GraphLoader {
    public abstract void loadJsonGraph(ArrayList<JsonNode> nodes, ArrayList<JsonEdge> edges) throws InvalidEdgeFormatException;
}
