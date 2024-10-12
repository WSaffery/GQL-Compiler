package ast.expressions.composite;

import java.util.ArrayList;
import java.util.List;

import ast.expressions.CompositeExpression;
import ast.expressions.Expression;
import enums.ValueComparator;

public record ComparisonExpression(
    Expression left, ValueComparator comparator, Expression right
    ) implements CompositeExpression {

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
