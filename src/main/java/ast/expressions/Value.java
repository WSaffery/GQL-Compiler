package ast.expressions;

// class must be non-sealed to allow cross-package inheritance without using modules
public non-sealed interface Value extends Expression {
    public Object getValue();
}
