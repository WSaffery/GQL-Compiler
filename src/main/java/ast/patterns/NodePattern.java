package ast.patterns;

import java.util.HashMap;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.label.LabelPattern;
import ast.patterns.label.WildcardLabel;

public final class NodePattern extends ElementPattern {
    public NodePattern(Optional<String> variableName, LabelPattern labelExpression, HashMap<String, Value> properties)
    {
        super(variableName, labelExpression, properties);
    }

    public static NodePattern getAnon()
    {
        return new NodePattern(Optional.empty(), new WildcardLabel(), new HashMap<>());
    }

    public String toString()
    {
        return "(%s: %s %s)".formatted(variableName, labelPattern, properties);
    }
}
