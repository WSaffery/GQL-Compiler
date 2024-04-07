package gql_gremlin.matching;

import java.util.EnumMap;
import java.util.List;
import enums.EvaluationMode;

public record GraphPattern(
    EnumMap<EvaluationMode, List<List<OrderedElementPattern>>> orderedPaths,
    List<MatchPattern> unrestrictedMatchPatterns
) {}
