package ast.expressions.atomic;

import ast.expressions.Value;

public record TruthValue(Boolean value) implements Value
{
    @Override
    public Object getValue() {
        return value;
    }
}