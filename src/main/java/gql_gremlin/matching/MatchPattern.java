package ast;

import java.util.List;

import ast.patterns.ElementPattern;

public record MatchPattern(
    KeyPattern headPattern,
    List<ElementPattern> middlePatterns,
    KeyPattern tailPattern
) 
{}