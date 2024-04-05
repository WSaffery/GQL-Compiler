package ast.patterns.label;

public final class Label extends LabelExpression
{
    String value;

    public Label(String value)
    {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}