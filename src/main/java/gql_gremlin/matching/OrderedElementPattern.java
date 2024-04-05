package gql_gremlin.matching;

import ast.patterns.ElementPattern;

// our element pattern is "preceded" if 
// * it is preceeded by a pattern outside the ending match statement with the same variable name
// in this case the compiler ought to
// * prior to the match statement use select if starting, and where otherwise
// * in the match step use where in both cases 
public record OrderedElementPattern(ElementPattern elementPattern, boolean preceded) {}
