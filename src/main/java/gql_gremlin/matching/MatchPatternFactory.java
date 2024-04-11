package gql_gremlin.matching;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import ast.patterns.ElementPattern;
import ast.patterns.PathPattern;
import enums.EvaluationMode;
import enums.EvaluationModeCategory;
import gql_gremlin.helpers.VariableOccurenceCounter;

public class MatchPatternFactory {

    // should probably drop enum map and just split into restricted vs non-restricted

    // TODO! this
    public static OrderedPathResult makeOrderedPaths(
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

        for (EvaluationMode mode : EvaluationMode.values()) 
            orderedPathPatterns.put(mode, new ArrayList<>());
        
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
                        varOccurences.putIfAbsent(variableName.get(), new VariableOccurenceCounter());
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

        return new OrderedPathResult(orderedPathPatterns, varOccurences);
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

}
