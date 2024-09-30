package ast.expressions.references;

import ast.expressions.ReferenceExpression;

public class CountNameExpression extends ReferenceExpression {
    public String name;

    public CountNameExpression(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}