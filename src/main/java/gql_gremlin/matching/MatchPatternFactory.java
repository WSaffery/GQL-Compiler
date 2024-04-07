package gql_gremlin.matching;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.PathPattern;
import enums.EvaluationMode;
import enums.EvaluationModeCategory;
import exceptions.SemanticErrorException;
import gql_gremlin.helpers.VariableOccurenceCounter;

public class MatchPatternFactory {

    // should probably drop enum map and just split into restricted vs non-restricted

    // TODO! this
    public static EnumMap<EvaluationMode, List<OrderedPathPattern>> makeOrderedPaths(
        EnumMap<EvaluationMode, List<PathPattern>> pathPatterns)
    {

        EvaluationMode[] evaluationOrder = {
            EvaluationMode.SIMPLE, EvaluationMode.ACYCLIC, 
            EvaluationMode.TRAIL, EvaluationMode.WALK };

        // count occurences of each variable name, broken down by eval mode category 
        // [unrestricted (WALK) vs restricted (the rest)]
        HashMap<String, VariableOccurenceCounter> varOccurences = new HashMap<>();

        EnumMap<EvaluationMode, List<OrderedPathPattern>> orderedPathPatterns = 
            new EnumMap<>(EvaluationMode.class);
        
        for (EvaluationMode mode : evaluationOrder)
        {
            final List<PathPattern> modePathPatterns = pathPatterns.get(mode);
            final List<OrderedPathPattern> modeOrderedPathPatterns = orderedPathPatterns.get(mode);

            for (PathPattern pathPattern : modePathPatterns)
            {
                final OrderedPathPattern orderedPathPattern = new OrderedPathPattern(pathPattern.variableName());

                for (ElementPattern elementPattern : pathPattern.pathSequence())
                {
                    final Optional<String> variableName = elementPattern.variableName();
                    if (variableName.isPresent())
                    {
                        final VariableOccurenceCounter counter = varOccurences.get(variableName.get());
                        final boolean preceeded = counter.preceeded();
                        counter.increment(mode);
                        orderedPathPattern.pathSequence().add(
                            new OrderedElementPattern(
                                elementPattern, Optional.of(counter), preceeded
                                )
                            );
                    }
                    else 
                    {
                        orderedPathPattern.pathSequence().add(
                            new OrderedElementPattern(
                                elementPattern, Optional.empty(), false
                                )
                            );
                    }                    
                }

                modeOrderedPathPatterns.add(orderedPathPattern);
            }
        }

        return orderedPathPatterns;
    }


    // combine many paths patterns into one list of match patterns, connecting connected paths by shared variable names
    // this removes all path context from execution so paths must be
    // * Unrestricted
    // * Uncaptured
    public static List<List<OrderedElementPattern>> makeMatchPatterns(List<OrderedPathPattern> pathPatterns)
    {
        final List<List<OrderedElementPattern>> matchPatterns = new ArrayList<>();

        for (OrderedPathPattern pathPattern : pathPatterns)
        {
            assert(!pathPattern.captured());

            final List<OrderedElementPattern> pathSequence = pathPattern.pathSequence();
            
            OrderedElementPattern firstPattern = pathSequence.get(0);
            List<OrderedElementPattern> patterns = new ArrayList<>();
            patterns.add(firstPattern);

            for (int i = 1; i < pathSequence.size(); i++)
            {
                final OrderedElementPattern pattern = pathSequence.get(i);
                patterns.add(pattern);
            
                // add our subpattern, ending at the current pattern
                if (pattern.intersection() || i == pathSequence.size() - 1)
                {
                    // can join on edges no cases because we're not targeting shortest
                    // paths or anything
                    
                    matchPatterns.add(patterns);

                    firstPattern = pattern;
                    patterns = new ArrayList<>();
                    patterns.add(pattern);
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
