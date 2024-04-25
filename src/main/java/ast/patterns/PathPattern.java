package ast.patterns;

import java.util.ArrayList;
import java.util.List;

import exceptions.SemanticErrorException;

public class PathPattern {
    ArrayList<PathComponent> pathSequence = new ArrayList<>();

    public PathPattern(ArrayList<PathComponent> pathSequence)
    {
        validatePathSequence(pathSequence);
        this.pathSequence = pathSequence;
    }

    public List<PathComponent> pathSequence() {
        return pathSequence;
    }

    public String toString()
    {
        return pathSequence.toString();
    }

    public static void validatePathSequence(ArrayList<PathComponent> pathSequence)
    {
        if (pathSequence.size() % 2 == 0) {
            System.out.println(pathSequence);   
            throw new SemanticErrorException("A path is an alternating sequence between nodes and edges, and must " +
                    "start and end with a node, hence, there must be an uneven number of element patterns in the list.");
        }

        for (int i = 0; i < pathSequence.size(); i++) {
            // checkPatternTypeAtCorrectPosition(pathSequence.get(i), i);
            final PathComponent component = pathSequence.get(i);
            final boolean pointIndex = i % 2 == 0; // nodes and paren paths (points) are at even indexes (zeroth, second, fourth, etc)
            final boolean validComponent = 
                 component instanceof EdgePattern && !pointIndex || 
                (component instanceof NodePattern || component instanceof ParenPathPattern) && pointIndex;
            
            if (!validComponent)
                throw new SemanticErrorException("The path does not alternate between nodes and edges.");
        }
    }

}
