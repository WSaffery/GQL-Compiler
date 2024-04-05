package ast;

import java.util.List;

public record MatchPattern(
    KeyPattern headPattern,
    List<ElementPattern> middlePatterns,
    KeyPattern tailPattern
) 
{}