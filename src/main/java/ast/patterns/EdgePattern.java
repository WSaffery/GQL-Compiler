package ast.patterns;

import java.util.HashMap;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.label.LabelExpression;
import enums.Direction;

public final class EdgePattern extends ElementPattern {
    public Optional<Direction> direction;

    public EdgePattern(Optional<String> variableName, LabelExpression labelExpression, HashMap<String, Value> properties, Optional<Direction> direction)
    {
        super(variableName, labelExpression, properties);
        this.direction = direction;
    }

    public String toString()
    {
        if (direction.isEmpty())
        {
            return "-[%s: %s %s]-".formatted(variableName, labelExpression, properties);
        }
        else if (direction.get() == Direction.LEFT_TO_RIGHT)
        {
            return "-[%s: %s %s]->".formatted(variableName, labelExpression, properties);
        } 
        else if (direction.get() == Direction.RIGHT_TO_LEFT)
        {
            return "<-[%s: %s %s]-".formatted(variableName, labelExpression, properties);
        }
        else if (direction.get() == Direction.UNDIRECTED) {
            return "~[%s: %s %s]~".formatted(variableName, labelExpression, properties);
        }
        
        return null;
    }
}
