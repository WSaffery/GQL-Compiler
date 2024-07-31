package ast.atoms;

public record Quantifier(
    int a,
    int b
) {
    public Quantifier(int a, int b)
    {
        // TODO! add support for 0 length iteration
        assert a >= 1 : "first part of quantifier is less than 1";
        assert b >= a : "second part of quantifier is less than first";
        this.a = a;
        this.b = b;
    }
}
