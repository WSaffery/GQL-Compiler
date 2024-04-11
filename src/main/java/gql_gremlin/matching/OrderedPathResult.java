package gql_gremlin.matching;

import java.util.List;
import java.util.Map;

import enums.EvaluationMode;
import gql_gremlin.helpers.VariableOccurenceCounter;

public record OrderedPathResult(
    Map<EvaluationMode, List<OrderedPathPattern>> orderedPaths,
    Map<String, VariableOccurenceCounter> variableOccurences
) {
    
}
