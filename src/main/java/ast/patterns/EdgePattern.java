package ast.patterns;

import java.util.HashMap;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.label.LabelExpression;
import enums.Direction;

public final class EdgePattern extends ElementPattern {
    public Direction direction;

    public EdgePattern(Optional<String> variableName, LabelExpression labelExpression, HashMap<String, Value> properties, Direction direction)
    {
        super(variableName, labelExpression, properties);
        this.direction = direction;
    }
}
