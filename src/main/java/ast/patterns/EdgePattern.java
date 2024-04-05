package ast.patterns;

import java.util.HashMap;

import ast.expressions.Value;
import ast.patterns.label.LabelExpression;
import enums.Direction;

public final class EdgePattern extends ElementPattern {
    public Direction direction;

    public EdgePattern(String variableName, LabelExpression labelExpression, HashMap<String, Value> properties, Direction direction)
    {
        super(variableName, labelExpression, properties);
        this.direction = direction;
    }
}
