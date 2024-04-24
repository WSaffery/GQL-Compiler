package ast.patterns;

import java.util.Optional;

import enums.EvaluationMode;

public record QualifiedPathPattern(
    Optional<String> variableName,
    EvaluationMode evaluationMode,
    PathPattern pathPattern
)
{ }