package ast.expressions.composite;

import ast.expressions.CompositeExpression;
import ast.expressions.Expression;
import enums.ValueComparator;

public record ComparisonExpression(
    Expression left, ValueComparator comparator, Expression right
    ) implements CompositeExpression {}
