package gql_gremlin;

import java.util.ArrayList;
import java.util.List;

import gql.patterns.ElementPattern;
import gql.patterns.PathPattern;


// label is null if pattern is unlabelled
record KeyPattern (
    String label,
    ElementPattern pattern
)
{}


public class MatchPatternFactory {

    // ...
    static List<MatchPattern> makeMatchPatterns(PathPattern pathPattern)
    {
        List<ElementPattern> patterns = pathPattern.getPathSequence();

        ElementPattern first = patterns.get(0);
        String firstLabel = first.variableName.getId();

        List<MatchPattern> matchPatterns = new ArrayList<>();
        List<ElementPattern> middlePatterns = new ArrayList<>();
        
        for (ElementPattern pattern : patterns.subList(1, patterns.size()))
        {
            if (pattern.variableName != null)
            {
                String currentLabel = pattern.variableName.getId();
                // add pattern starting with the prior first element and ending with the next labelled element 
                // with any unlabelled elements inbetween in middlePatterns
                MatchPattern p = new MatchPattern(
                    new KeyPattern(firstLabel, first), 
                    middlePatterns, 
                    new KeyPattern(currentLabel, pattern));
                matchPatterns.add(p);

                first = pattern;
                firstLabel = currentLabel;
                middlePatterns = new ArrayList<>();
            }
            else 
            {
                middlePatterns.add(pattern);
            }
        }

        System.out.println("match patterns: " + matchPatterns);
        

        return matchPatterns;
    }
}
