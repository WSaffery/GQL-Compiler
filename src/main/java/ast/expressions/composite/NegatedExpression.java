package ast.expressions.composite;

import ast.expressions.CompositeExpression;
import ast.expressions.Expression;

public record NegatedExpression(Expression subExpression) implements CompositeExpression {}
