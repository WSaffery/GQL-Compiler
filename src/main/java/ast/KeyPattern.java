package ast;

// label is null if pattern is unlabelled
public record KeyPattern (
    String label,
    ElementPattern pattern
)
{}