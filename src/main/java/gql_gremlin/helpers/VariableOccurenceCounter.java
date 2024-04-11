package gql_gremlin.helpers;

import java.util.EnumMap;
import java.util.Map;

import enums.EvaluationMode;
import enums.EvaluationModeCategory;

public class VariableOccurenceCounter {
    EnumMap<EvaluationModeCategory, Integer> counts = new EnumMap<>(Map.of(
        EvaluationModeCategory.UNRESTRICTED, 0, EvaluationModeCategory.RESTRICTED, 0));
    
    public Map<EvaluationModeCategory, Integer> counts()
    {
        return counts;
    }

    // returns true if the variable has been previously referenced
    // by a path pattern with an evaluation mode in the RESTRICTED category
    public boolean preceeded()
    {
        return counts.get(EvaluationModeCategory.RESTRICTED) > 0;
    }

    // returns true if the variable is reference by multiple path patterns
    public boolean intersection()
    {
        return counts.get(EvaluationModeCategory.UNRESTRICTED) + counts.get(EvaluationModeCategory.RESTRICTED) > 1;
    }

    public void increment(EvaluationModeCategory category)
    {
        counts.compute(category, (k, v) -> v+1);
    }

    public void increment(EvaluationMode mode)
    {
        increment(EvaluationModeCategory.getEvaluationModeCategory(mode));
    }

}
