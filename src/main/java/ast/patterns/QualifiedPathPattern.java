package ast.patterns;

import java.util.Optional;

import enums.EvaluationMode;

public record QualifiedPathPattern(
    Optional<String> variableName,
    EvaluationMode evaluationMode,
    PathPattern pathPattern
)
{

    // evaluation mode is walk, no variable name captures the path
    public boolean ordinary()
    {
        return this.evaluationMode == EvaluationMode.WALK && this.variableName.isEmpty();
    }

    public static QualifiedPathPattern makeOrdinary(PathPattern pathPattern)
    {
        return new QualifiedPathPattern(Optional.empty(), EvaluationMode.WALK, pathPattern);
    }
}