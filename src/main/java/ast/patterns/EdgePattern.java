package ast.patterns;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.label.LabelPattern;
import ast.patterns.label.WildcardLabel;
import enums.Direction;

public final class EdgePattern extends ElementPattern {
    public Optional<Direction> direction;

    public EdgePattern(Optional<String> variableName, LabelPattern labelExpression, HashMap<String, Value> properties, Optional<Direction> direction)
    {
        super(variableName, labelExpression, properties);
        this.direction = direction;
    }

    public EdgePattern reversed()
    {
        return new EdgePattern(variableName, labelPattern, properties, direction.map(d -> d.reversed()));
    }

    public EdgePattern getNoPredicate()
    {
        return new EdgePattern(variableName, new WildcardLabel(), new HashMap<>(), direction);
    }

    public String toString()
    {
        String varString = variableName.orElse("");
        String propertyString = properties.size() > 0 ? " %s".formatted(properties) : "";
        List<String> matchedLabels = LabelPattern.matchedLabels(labelPattern);
        Object labelsObject;
        
        // bad 
        if (matchedLabels.size() == 0) labelsObject = "";
        else if (matchedLabels.size() == 1) labelsObject = matchedLabels.get(0);
        else labelsObject = matchedLabels;

        if (direction.isEmpty())
        {
            return "-[%s:%s%s]-".formatted(varString, labelsObject, propertyString);
        }
        else if (direction.get() == Direction.LEFT_TO_RIGHT)
        {
            return "-[%s:%s%s]->".formatted(varString, labelsObject, propertyString);
        } 
        else if (direction.get() == Direction.RIGHT_TO_LEFT)
        {
            return "<-[%s:%s%s]-".formatted(varString, labelsObject, propertyString);
        }
        else if (direction.get() == Direction.UNDIRECTED) {
            return "~[%s:%s%s]~".formatted(varString, labelsObject, propertyString);
        }
        
        return null;
    }
}
