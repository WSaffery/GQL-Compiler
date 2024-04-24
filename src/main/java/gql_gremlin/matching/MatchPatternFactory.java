package gql_gremlin.matching;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import ast.patterns.ElementPattern;
import ast.patterns.ParenPathPattern;
import ast.patterns.PathComponent;
import ast.patterns.PathPattern;
import ast.patterns.QualifiedPathPattern;
import enums.EvaluationMode;
import enums.EvaluationModeCategory;
import exceptions.SemanticErrorException;
import gql_gremlin.helpers.VariableOccurenceCounter;

public class MatchPatternFactory {

    // should probably drop enum map and just split into restricted vs non-restricted

    public static void addVariables(PathPattern path, Set<String> variables)
    {
        for (PathComponent component : path.pathSequence())
        {
            if (component instanceof ParenPathPattern)
            {
                ParenPathPattern parenPath = (ParenPathPattern) component;
                addVariables(parenPath.pathPattern(), variables);
            }
            else 
            {
                assert component instanceof ElementPattern;
                ElementPattern elem = (ElementPattern) component;
                elem.variableName().ifPresent(name -> variables.add(name));
            }
        }    
    }

    // verifies there is no variables in parenthesised subpaths that are used outside of their core path.
    public void verifyNesting(List<QualifiedPathPattern> pathPatterns)
    {
        HashSet<String> nestedVariables = new HashSet<>();
        HashSet<String> variables = new HashSet<>();

        for (QualifiedPathPattern pathPattern : pathPatterns)
        {
            HashSet<String> newNestedVariables = new HashSet<>();
            HashSet<String> newVariables = new HashSet<>();

            for (PathComponent component : pathPattern.pathPattern().pathSequence())
            {
                if (component instanceof ParenPathPattern)
                {
                    // a single path pattern forms a tree of references on its variables
                    // because of nesting from paren path
                    // however in the ordering case we're only interested in references across paths
                    // 
                    ParenPathPattern parenPath = (ParenPathPattern) component;
                    addVariables(parenPath.pathPattern(), newNestedVariables);
                    
                    HashSet<String> priorVariables = new HashSet<>();
                    priorVariables.addAll(nestedVariables);
                    priorVariables.addAll(variables);

                    priorVariables.retainAll(newNestedVariables); // convert to intersection

                    if (priorVariables.size() > 0)
                    {
                        throw new SemanticErrorException("Intersection of nested variable with other variable across paths");
                    }
                }
                else 
                {
                    assert component instanceof ElementPattern;
                    ElementPattern elem = (ElementPattern) component;
                    Optional<String> varName = elem.variableName();
                    if (varName.isPresent())
                    {
                        String name = varName.get();
                        newVariables.add(name);
                        if (nestedVariables.contains(name))
                        {
                            throw new SemanticErrorException("Intersection of variable with nested variable across paths");
                        }
                    }
                }
            }

            nestedVariables.addAll(newNestedVariables);
            variables.addAll(newVariables);
        }
    }

    // TODO! this
    public static Map<String, VariableReferences> getVariableReferenceMap(
        List<QualifiedPathPattern> restrictedPathPatterns,
        List<PathPattern> unrestrictedPathPatterns)
    {
        HashMap<String, VariableReferences> variableReferenceMap = new HashMap<>();
        // variables nested in parenthesied paths ought not to have cross references at all

        for (int i = 0; i < restrictedPathPatterns.size(); i++)
        {
            QualifiedPathPattern pattern = restrictedPathPatterns.get(i);

            for (PathComponent component : pattern.pathPattern().pathSequence())
            {
                if (component instanceof ElementPattern)
                {
                    ElementPattern elem = (ElementPattern) component;
                    Optional<String> varName = elem.variableName();
                    if (varName.isPresent())
                    {
                        String name = varName.get();
                        variableReferenceMap.putIfAbsent(name, new VariableReferences(new ArrayList<>()));
                        variableReferenceMap.get(name).restrictedReferenceIndices().add(i);
                    }
                }
                else 
                {
                    assert component instanceof ParenPathPattern;
                }
            }
        }

        for (PathPattern pathPattern : unrestrictedPathPatterns)
        {
            for (PathComponent component : pathPattern.pathSequence())
            {
                if (component instanceof ElementPattern)
                {
                    ElementPattern elem = (ElementPattern) component;
                    Optional<String> varName = elem.variableName();
                    varName.ifPresent(
                        name -> {
                            variableReferenceMap.putIfAbsent(name, new VariableReferences(new ArrayList<>()));
                            // variableReferenceMap.get(name).restrictedReferenceIndices().add(i);
                            variableReferenceMap.compute(name, (n, r) -> r.addUnrestrictedReference());
                        }
                    );
                }
                else 
                {
                    assert component instanceof ParenPathPattern;
                }
            }
        }

        return variableReferenceMap;
    }


    public static Optional<String> getJointVariable(
        Map<String, VariableOccurenceCounter> variableOccurences
    )
    {
        Optional<String> res = Optional.empty();
        int unrestrictedCount = 0;

        for (Entry<String, VariableOccurenceCounter> e : variableOccurences.entrySet())
        {
            final VariableOccurenceCounter counter = e.getValue();
            final Map<EvaluationModeCategory, Integer> counts = counter.counts();
            
            if (counts.get(EvaluationModeCategory.RESTRICTED) > 1)
            {
                int currentUnrestrictedCount = counts.get(EvaluationModeCategory.UNRESTRICTED);
                if (currentUnrestrictedCount > unrestrictedCount)
                {
                    res = Optional.of(e.getKey());
                    unrestrictedCount = currentUnrestrictedCount;
                }
            }

        }

        return res;
    }


    // combine many paths patterns into one list of match patterns, connecting connected paths by shared variable names
    // this removes all path context from execution so paths must be
    // * Unrestricted
    // * Uncaptured

    public static List<PathPattern> makeMatchPatterns(List<PathPattern> pathPatterns, Map<String, VariableReferences> referenceMap)
    {
        final List<PathPattern> matchPatterns = new ArrayList<>();

        for (PathPattern pathPattern : pathPatterns)
        {
            // assert(!pathPattern.captured());
            final List<PathComponent> pathSequence = pathPattern.pathSequence();
            
            int firstIntersection = -1;
            for (int i = 0; i < pathSequence.size(); i++)
            {
                final PathComponent component = pathSequence.get(i);
                if (component instanceof ElementPattern)
                {
                    final ElementPattern elem = (ElementPattern) component;
                    final boolean intersection = 
                        elem.variableName().isPresent() && 
                        referenceMap.containsKey(elem.variableName().get()) &&
                        referenceMap.get(elem.variableName().get()).intersection();
                    
                    if (intersection)
                    {
                        firstIntersection = i;
                        break;
                    }
                }
            }

            // add the full pattern and continue
            if (firstIntersection == -1 || firstIntersection == pathSequence.size())
            {
                matchPatterns.add(pathPattern); // add original path pattern unchanged
                continue;
            }

            // add the first subpattern and the rest
            matchPatterns.add(new PathPattern(new ArrayList<>(pathSequence.subList(0, firstIntersection))));


            PathComponent firstComponent = pathSequence.get(firstIntersection);
            ArrayList<PathComponent> components = new ArrayList<>();
            components.add(firstComponent);

            for (int i = 1; i < pathSequence.size(); i++)
            {
                final PathComponent component = pathSequence.get(i);
                components.add(component);

                boolean intersection = false;
                if (component instanceof ElementPattern)
                {
                    final ElementPattern elem = (ElementPattern) component;
                    intersection = 
                        elem.variableName().isPresent() && 
                        referenceMap.containsKey(elem.variableName().get()) &&
                        referenceMap.get(elem.variableName().get()).intersection();

                }

                if (intersection || i == pathSequence.size() - 1)
                {
                    // can join on edges no cases because we're not targeting shortest
                    // paths or anything
                    // huh? unsure what above means
                    matchPatterns.add(new PathPattern(components));
                }
                if (intersection)
                {
                    firstComponent = component;
                    components = new ArrayList<>();
                    components.add(firstComponent);
                }
            }
        }

        return matchPatterns;
    }


    // public static List<List<OrderedElementPattern>> makeMatchPatterns(List<OrderedPathPattern> pathPatterns)
    // {
    //     final List<List<OrderedElementPattern>> matchPatterns = new ArrayList<>();

    //     for (OrderedPathPattern pathPattern : pathPatterns)
    //     {
    //         assert(!pathPattern.captured());

    //         final List<OrderedElementPattern> pathSequence = pathPattern.pathSequence();
            
    //         OrderedElementPattern firstPattern = pathSequence.get(0);
    //         List<OrderedElementPattern> patterns = new ArrayList<>();
    //         patterns.add(firstPattern);

    //         for (int i = 1; i < pathSequence.size(); i++)
    //         {
    //             final OrderedElementPattern pattern = pathSequence.get(i);
    //             patterns.add(pattern);
            
    //             // add our subpattern, ending at the current pattern
    //             if (pattern.intersection() || i == pathSequence.size() - 1)
    //             {
    //                 // can join on edges no cases because we're not targeting shortest
    //                 // paths or anything
                    
    //                 matchPatterns.add(patterns);

    //                 firstPattern = pattern;
    //                 patterns = new ArrayList<>();
    //                 patterns.add(pattern);
    //             }
    //         }
    //     }

    //     return matchPatterns;
    // }

}
