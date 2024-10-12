package ast.patterns;

import java.util.HashMap;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.label.LabelPattern;

public abstract sealed class ElementPattern implements PathComponent permits EdgePattern, NodePattern {
    public Optional<String> variableName;
    public LabelPattern labelPattern;
    public HashMap<String, Value> properties;

    ElementPattern(Optional<String> variableName, LabelPattern labelExpression, HashMap<String, Value> properties)
    {
        this.variableName = variableName;
        this.labelPattern = labelExpression;
        this.properties = properties;
    }

    public Optional<String> variableName() { return variableName; };
    public LabelPattern labelPattern() { return labelPattern; };
    public HashMap<String, Value> properties() { return properties; };
    
}
