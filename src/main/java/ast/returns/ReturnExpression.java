package ast.returns;

import ast.expressions.Expression;
import ast.expressions.references.NameExpression;

public record ReturnExpression(Expression expression, String name) implements ReturnItem {

    public ReturnExpression(NameExpression expression)
    {
        this(expression, expression.name());
    }
}
