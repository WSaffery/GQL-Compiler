package ast.patterns;

import enums.EvaluationMode;

public record QualifiedPathPattern(
    PathPattern pathPattern,
    EvaluationMode evaluationMode
)
{ }