package ast.patterns;

import java.util.HashMap;
import java.util.List;
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

    public NodePattern getNoPredicate()
    {
        return new NodePattern(variableName, new WildcardLabel(), new HashMap<>());
    }

    public String toString()
    {
        String propertyString = properties.size() > 0 ? " %s".formatted(properties) : "";
        List<String> matchedLabels = LabelPattern.matchedLabels(labelPattern);
        Object labelsObject;
        
        // bad 
        if (matchedLabels.size() == 0) labelsObject = "";
        else if (matchedLabels.size() == 1) labelsObject = matchedLabels.get(0);
        else labelsObject = matchedLabels;

        return "(%s:%s%s)".formatted(
                variableName.orElse(""), 
                labelsObject,
                propertyString);
    }
}
