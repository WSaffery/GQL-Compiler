package ast.patterns;

import java.util.ArrayList;
import java.util.List;

import exceptions.SemanticErrorException;

public class PathPattern {
    String variableName;
    ArrayList<ElementPattern> pathSequence = new ArrayList<>();

    public PathPattern(String variable, ArrayList<ElementPattern> pathSequence)
    {
        validatePathSequence(pathSequence);
        this.variableName = variable;
        this.pathSequence = pathSequence;
    }

    public List<ElementPattern> getPathSequence() {
        return pathSequence;
    }

    public static void validatePathSequence(ArrayList<ElementPattern> pathSequence)
    {
        if (pathSequence.size() % 2 == 0) {
            throw new SemanticErrorException("A path is an alternating sequence between nodes and edges, and must " +
                    "start and end with a node, hence, there must be an uneven number of element patterns in the list.");
        }

        for (int i = 0; i < pathSequence.size(); i++) {
            // checkPatternTypeAtCorrectPosition(pathSequence.get(i), i);
            final ElementPattern element = pathSequence.get(i);
            final boolean nodeIndex = i % 2 == 0; // nodes are at odd indexes (zeroth, second, fourth, etc)
            final boolean validElement = 
                element instanceof EdgePattern && !nodeIndex || 
                element instanceof NodePattern && nodeIndex;
            
            if (!validElement)
                throw new SemanticErrorException("The path does not alternate between nodes and edges.");
        }
    }

}
