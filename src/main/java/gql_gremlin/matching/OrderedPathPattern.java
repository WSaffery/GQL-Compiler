package gql_gremlin.matching;

import java.util.ArrayList;

public record OrderedPathPattern(String variableName, ArrayList<OrderedElementPattern> pathSequence) {};
