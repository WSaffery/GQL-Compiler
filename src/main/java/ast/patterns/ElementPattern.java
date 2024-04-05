package ast.patterns;

import java.util.HashMap;

import ast.expressions.Value;
import ast.patterns.label.LabelExpression;

public abstract sealed class ElementPattern permits EdgePattern, NodePattern {
    public String variableName;
    public LabelExpression labelExpression;
    public HashMap<String, Value> properties;

    ElementPattern(String variableName, LabelExpression labelExpression, HashMap<String, Value> properties)
    {
        this.variableName = variableName;
        this.labelExpression = labelExpression;
        this.properties = properties;
    }

    public String variableName() { return variableName; };
    public LabelExpression labelExpression() { return labelExpression; };
    public HashMap<String, Value> properties() { return properties; };
}
