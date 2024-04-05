package ast.expressions;

import ast.expressions.atomic.TruthValue;
import ast.expressions.composite.BooleanConjunctionExpression;
import ast.expressions.composite.ComparisonExpression;
import ast.expressions.composite.NegatedExpression;

public abstract sealed interface Expression permits 
    Value, CompositeExpression, ReferenceExpression {

     
    // check if the expression's result is of type boolean
    public static boolean isBooleanExpression(Expression expression)
    {
        return expression instanceof TruthValue || 
               expression instanceof ComparisonExpression ||
               expression instanceof NegatedExpression ||
               expression instanceof BooleanConjunctionExpression;
    }

}
