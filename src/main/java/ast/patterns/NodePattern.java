package ast.patterns;

import java.util.HashMap;

import ast.expressions.Value;
import ast.patterns.label.LabelExpression;

public final class NodePattern extends ElementPattern {
    public NodePattern(String variableName, LabelExpression labelExpression, HashMap<String, Value> properties)
    {
        super(variableName, labelExpression, properties);
    }
}
