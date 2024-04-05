package gql_gremlin.matching;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.PathPattern;
import enums.EvaluationMode;
import exceptions.SemanticErrorException;

public class MatchPatternFactory {

    // should probably drop enum map and just split into restricted vs non-restricted

    // TODO! this
    public static EnumMap<EvaluationMode, List<MatchPattern>> makeMatchPatterns(
        EnumMap<EvaluationMode, List<PathPattern>> pathPatterns)
    {
        EvaluationMode[] pathRestrictedModes = {
            EvaluationMode.SIMPLE, EvaluationMode.ACYCLIC, 
            EvaluationMode.TRAIL};
        
        EvaluationMode[] pathUnrestrictedModes = { EvaluationMode.WALK };

        EvaluationMode[] evaluationOrder = {
            EvaluationMode.SIMPLE, EvaluationMode.ACYCLIC, 
            EvaluationMode.TRAIL, EvaluationMode.WALK };

        HashMap<String, Integer> varOccurences = new HashMap<>();
        EnumMap<EvaluationMode, List<List<OrderedElementPattern>>> orderedPathPatterns = new EnumMap<>(EvaluationMode.class);
        
        for (EvaluationMode mode : evaluationOrder)
        {
            final List<PathPattern> modePathPatterns = pathPatterns.get(mode);
            // final List<> modeOrderedPathPatterns = orderedPathPatterns.get(mode);

            for (PathPattern pathPattern : modePathPatterns)
            {
                for (ElementPattern elementPattern : pathPattern.getPathSequence())
                {
                    final String variableName = elementPattern.variableName();
                    final Integer occurences = varOccurences.get(variableName);
                    if (occurences == 0)
                    {
                        varOccurences.put(variableName, 1);

                    }
                    // varOccurences.merge(variableName, 1, Integer::sum); // place 1 if not found, otherwise add 1

                }
            }
        }

        return null;

        // assert(EvaluationMode.values().length == executionOrder.length); // incase more modes are added
        


    }



    public static List<MatchPattern> makeMatchPatterns(List<PathPattern> pathPatterns)
    {
        HashMap<String, Integer> varOccurences = new HashMap<>();
        
        for (PathPattern pathPattern : pathPatterns)
        {
            for (ElementPattern elementPattern : pathPattern.getPathSequence())
            {
                final String variableName = elementPattern.variableName();
                varOccurences.merge(variableName, 1, Integer::sum); // place 1 if not found, otherwise add 1
            }
        }

        List<MatchPattern> matchPatterns = new ArrayList<>();

        for (PathPattern pathPattern : pathPatterns)
        {
            List<ElementPattern> pathSequence = pathPattern.getPathSequence();
            
            ElementPattern firstPattern = pathSequence.get(0);
            List<OrderedElementPattern> middlePatterns = new ArrayList<>();

            for (int i = 1; i < pathSequence.size(); i++)
            {
                final ElementPattern pattern = pathSequence.get(i);
                final String currentVariable = pattern.variableName();

                // add our subpattern, ending at the current pattern
                if (varOccurences.get(currentVariable) > 1 || i == pathSequence.size() - 1)
                {
                    if (pattern instanceof EdgePattern)
                    {
                        // !TODO: evaluate this by cases
                        throw new SemanticErrorException("Joining patterns on edge variable's unsupported");
                    }
                    
                    MatchPattern p = new MatchPattern(
                        new OrderedElementPattern(firstPattern, false),
                        middlePatterns, 
                        new OrderedElementPattern(pattern, false)
                    );
                    matchPatterns.add(p);

                    firstPattern = pattern;
                    middlePatterns = new ArrayList<>();
                }
                else { // keep building the subpattern
                    middlePatterns.add(new OrderedElementPattern(pattern, false));
                }
            }
        }

        return matchPatterns;
    }


    /**
    // ...
    // public static List<MatchPattern> makeMatchPatterns(PathPattern pathPattern)
    // {
    //     List<ElementPattern> patterns = pathPattern.getPathSequence();

    //     ElementPattern first = patterns.get(0);
    //     String firstLabel = first.variableName;

    //     List<MatchPattern> matchPatterns = new ArrayList<>();
    //     List<ElementPattern> middlePatterns = new ArrayList<>();
        
    //     for (ElementPattern pattern : patterns.subList(1, patterns.size()))
    //     {
    //         if (pattern.variableName != null)
    //         {
    //             String currentLabel = pattern.variableName;
    //             // add pattern starting with the prior first element and ending with the next labelled element 
    //             // with any unlabelled elements inbetween in middlePatterns
    //             MatchPattern p = new MatchPattern(
    //                 new KeyPattern(firstLabel, first), 
    //                 middlePatterns, 
    //                 new KeyPattern(currentLabel, pattern));
    //             matchPatterns.add(p);

    //             first = pattern;
    //             firstLabel = currentLabel;
    //             middlePatterns = new ArrayList<>();
    //         }
    //         else 
    //         {
    //             middlePatterns.add(pattern);
    //         }
    //  }

    //     System.out.println("match patterns: " + matchPatterns);
        

    //     return matchPatterns;
    // }
    **/

}
