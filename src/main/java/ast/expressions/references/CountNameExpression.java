package ast.expressions.references;

import java.util.List;

import ast.expressions.ReferenceExpression;

public class CountNameExpression extends ReferenceExpression {
    public String name;

    public CountNameExpression(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public List<String> referencedVariables()
    {
        return List.of(name);
    }

    @Override
    public String toString() {
        return name.toString();
    }
}