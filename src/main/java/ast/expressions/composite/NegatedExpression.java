package ast.expressions.composite;

import java.util.List;

import ast.expressions.CompositeExpression;
import ast.expressions.Expression;

public record NegatedExpression(Expression subExpression) implements CompositeExpression {
    public List<String> referencedVariables()
    {
        return subExpression.referencedVariables();
    }
}
