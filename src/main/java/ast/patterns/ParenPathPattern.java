package ast.patterns;

import ast.atoms.Quantifier;
import ast.expressions.Expression;

public record ParenPathPattern(
    PathPattern pathPattern,
    Expression whereExpression,
    Quantifier quantifier
) implements PathComponent {
}
