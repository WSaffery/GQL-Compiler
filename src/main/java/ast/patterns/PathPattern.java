package ast.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.javatuples.Pair;

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

    private static Optional<String> getComponentVariableName(PathComponent component)
    {
        if (component instanceof ElementPattern)
            {
                return ((ElementPattern)component).variableName();
            }
        else { // paren path
            return Optional.empty();
        }
    }

    public List<Optional<String>> variableSequence()
    {
        return pathSequence.stream().map(PathPattern::getComponentVariableName).toList();
    }

    public Optional<String> headVariableName()
    {
        return getComponentVariableName(pathSequence.get(0));
    }

    // take pattern (p1, p2, ..., pn)
    // return two new patterns
    // reversed((p1, p2, ..., pi)), (pi+1, pi+2, ..., pn)
    // where in the former pi is now the first pattern, and all edge directions are reversed
    public Pair<PathPattern, PathPattern> split(int idx)
    {
        PathPattern b = new PathPattern(new ArrayList<>(pathSequence.subList(idx, pathSequence.size())));
        ArrayList<PathComponent> aComponents = new ArrayList<>();
        
        // in reverse order, starting from idx
        for (int i = idx; i >= 0; i--)
        {
            PathComponent component = pathSequence.get(i);

            // if the component is an edge, change it to its reversed form
            if (component instanceof ElementPattern)
            {
                ElementPattern elementPattern = (ElementPattern) component;
                if (elementPattern instanceof EdgePattern)
                {
                    EdgePattern edge = (EdgePattern) elementPattern;
                    component = edge.reversed();
                }
            }

            // add the component
            aComponents.add(component);
        }

        PathPattern a = new PathPattern(aComponents);

        return new Pair<>(a, b);
    }

    // if the only previously captured variable is our last
    // we ought to just flip the pattern
    public PathPattern reversed()
    {
        ArrayList<PathComponent> components = new ArrayList<>();
        
        // in reverse order, starting from idx
        for (int i = pathSequence.size()-1; i >= 0; i--)
        {
            PathComponent component = pathSequence.get(i);

            // if the component is an edge, change it to its reversed form
            if (component instanceof ElementPattern)
            {
                ElementPattern elementPattern = (ElementPattern) component;
                if (elementPattern instanceof EdgePattern)
                {
                    EdgePattern edge = (EdgePattern) elementPattern;
                    component = edge.reversed();
                }
            }

            // add the component
            components.add(component);
        }

        return new PathPattern(components);
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
