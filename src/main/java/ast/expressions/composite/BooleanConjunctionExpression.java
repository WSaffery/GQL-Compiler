package ast.expressions.composite;

import java.util.ArrayList;
import java.util.List;

import ast.expressions.CompositeExpression;
import ast.expressions.Expression;
import enums.BooleanComparator;
import exceptions.SemanticErrorException;

public final class BooleanConjunctionExpression implements CompositeExpression {
    private final BooleanComparator comparator;
    private Expression left;
    private Expression right;

    public BooleanConjunctionExpression(Expression left, BooleanComparator comparator, Expression right) {
        if (!Expression.isBooleanExpression(left)) throw new SemanticErrorException("Invalid value type: left side " + left.toString() + " should evaluate to a truth value.");
        if (!Expression.isBooleanExpression(right)) throw new SemanticErrorException("Invalid value type: right " + right.toString() + " side should evaluate to a truth value.");

        this.left = left;
        this.comparator = comparator;
        this.right = right;
    }

    public BooleanComparator comparator() { return comparator; }
    public Expression left() { return left; }
    public Expression right() { return right; }

    @Override
    public String toString() {
        return left.toString() + " " + comparator.toString() + " " + right.toString();
    }

    // Could use a cheaper concat optimised list wrapper instead
    // i.e. https://stackoverflow.com/a/13868352
    // leaving for now
    public List<String> referencedVariables()
    {
        ArrayList<String> names = new ArrayList<>(right.referencedVariables());
        names.addAll(left.referencedVariables());
        return names;
    }
}
