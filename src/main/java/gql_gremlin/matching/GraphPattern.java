package gql_gremlin.matching;

import java.util.List;

public record GraphPattern(
    List<MatchPattern> simpleMatchPatterns,
    List<MatchPattern> trailMatchPatterns,
    List<MatchPattern> walkMatchPatterns
) {}
