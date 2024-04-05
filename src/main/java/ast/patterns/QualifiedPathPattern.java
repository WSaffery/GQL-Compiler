import gql_gremlin.enums.EvaluationMode;

public record QualifiedPathPattern(
    PathPattern pathPattern,
    EvaluationMode evaluationMode
)
{ }