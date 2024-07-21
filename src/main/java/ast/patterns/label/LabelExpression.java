package ast.patterns.label;

public abstract sealed class LabelExpression extends LabelPattern 
    permits Label, BinaryLabelExpression {}
