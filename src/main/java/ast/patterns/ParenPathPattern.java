package ast.patterns;

import java.util.Optional;

import ast.atoms.Quantifier;
import ast.expressions.Expression;

public record ParenPathPattern(
    PathPattern pathPattern,
    Expression whereExpression,
    Quantifier quantifier,
    Optional<NodePattern> headOuterNode,
    Optional<NodePattern> tailOuterNode
) implements PathComponent {

    public ParenPathPattern dropHead()
    {
        return new ParenPathPattern(pathPattern, whereExpression, quantifier, Optional.empty(), tailOuterNode);
    }
}
