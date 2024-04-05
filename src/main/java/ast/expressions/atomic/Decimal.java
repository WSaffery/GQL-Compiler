package ast.expressions.atomic;

import ast.expressions.Value;

public record Decimal(double value) implements Value
{
    @Override
    public Object getValue() {
        return value;
    }
}