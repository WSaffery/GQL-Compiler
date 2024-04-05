package gql_gremlin.matching;

import java.util.List;

public record MatchPattern(
    OrderedElementPattern headPattern,
    List<OrderedElementPattern> middlePatterns,
    OrderedElementPattern tailPattern
) 
{}