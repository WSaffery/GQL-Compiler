package gql_gremlin;

import java.util.List;

import gql.patterns.ElementPattern;

public record MatchPattern(
    KeyPattern headPattern,
    List<ElementPattern> middlePatterns,
    KeyPattern tailPattern
) 
{}