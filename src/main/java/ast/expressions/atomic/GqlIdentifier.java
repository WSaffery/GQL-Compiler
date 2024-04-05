package ast.expressions.atomic;

import ast.expressions.Value;

public record GqlIdentifier(String value) implements Value 
{
    @Override
    public Object getValue() {
        return value;
    }
}
