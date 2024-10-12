package gql_gremlin.query;

import java.util.HashSet;
import java.util.Set;

import ast.patterns.label.BinaryLabelExpression;
import ast.patterns.label.BinaryLabelOperator;
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import ast.patterns.label.LabelPattern;
import ast.patterns.label.WildcardLabel;
import exceptions.UnsupportedFeatureException;

public class QueryElement {
    protected static Set<String> calculateLabels(LabelPattern pattern)
    {
        if (pattern instanceof WildcardLabel)
        {
            return Set.of(); // vacuous empty set
        }

        if (pattern instanceof Label)
        {
            Label label = (Label) pattern;
            return Set.of(label.getValue());
        }

        if (pattern instanceof BinaryLabelExpression)
        {
            HashSet<String> labels = new HashSet<>();
            BinaryLabelExpression expr = (BinaryLabelExpression) pattern;
            if (expr.operator() != BinaryLabelOperator.OR)
            {
                throw new UnsupportedFeatureException("AND label expression found, Only single labels are supported currently");
            }

            for (LabelExpression subExpr : expr.operandExpressions())
            {
                labels.addAll(calculateLabels(subExpr));
            }
            
            return labels;
        }

        throw new RuntimeException("Unexpected LabelPattern type");
    }
}
