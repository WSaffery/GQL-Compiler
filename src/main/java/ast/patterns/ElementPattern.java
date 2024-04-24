package ast.patterns;

import java.util.HashMap;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.label.LabelExpression;

public abstract sealed class ElementPattern implements PathComponent permits EdgePattern, NodePattern {
    public Optional<String> variableName;
    public LabelExpression labelExpression;
    public HashMap<String, Value> properties;

    ElementPattern(Optional<String> variableName, LabelExpression labelExpression, HashMap<String, Value> properties)
    {
        this.variableName = variableName;
        this.labelExpression = labelExpression;
        this.properties = properties;
    }

    public Optional<String> variableName() { return variableName; };
    public LabelExpression labelExpression() { return labelExpression; };
    public HashMap<String, Value> properties() { return properties; };
}
