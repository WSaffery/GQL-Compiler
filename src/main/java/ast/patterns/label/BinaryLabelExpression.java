package ast.patterns.label;

import java.util.List;

public final class BinaryLabelExpression extends LabelExpression {
    List<LabelExpression> operandExpressions;
    BinaryLabelOperator operator;

    public BinaryLabelExpression(
        List<LabelExpression> operandExpressions, 
        BinaryLabelOperator operator
    )
    {
        this.operandExpressions = operandExpressions;
        this.operator = operator;
    }

    public List<LabelExpression> operandExpressions() { return operandExpressions; }
    public BinaryLabelOperator operator() { return operator; }
}
