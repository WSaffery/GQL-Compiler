package gql.patterns;

import gql.enums.EvaluationMode;

public record QualifiedPathPattern(
    PathPattern pathPattern,
    EvaluationMode evaluationMode
)
{ }