package ast.expressions;

import java.util.List;

import ast.expressions.atomic.GqlString;
import ast.expressions.atomic.TruthValue;
import ast.expressions.composite.BooleanConjunctionExpression;
import ast.expressions.composite.ComparisonExpression;
import ast.expressions.composite.NegatedExpression;
import ast.expressions.graph.GraphExistsExpression;
import ast.expressions.references.NameExpression;
import ast.expressions.references.PropertyReference;

public abstract sealed interface Expression permits 
    Value, CompositeExpression, ReferenceExpression, GraphExpression {

    default public List<String> referencedVariables()
    {
        return List.of();
    }
    
    // check if the expression's result is of type boolean
    public static boolean isBooleanExpression(Expression expression)
    {
        return expression instanceof TruthValue || 
               expression instanceof ComparisonExpression ||
               expression instanceof NegatedExpression ||
               expression instanceof BooleanConjunctionExpression ||
               expression instanceof GraphExistsExpression;
    }

    public static boolean isReferenceExpression(Expression expression)
    {
        return expression instanceof NameExpression || 
               expression instanceof PropertyReference;
    }

    public static boolean isValue(Expression expression)
    {
        return expression instanceof GqlString || 
               expression instanceof TruthValue;
    }

}
