package ast.atoms;

public record Quantifier(
    int a,
    int b
) {
    public Quantifier(int a, int b)
    {
        assert a > 0;
        assert b >= a;
        this.a = a;
        this.b = b;
    }
}
