package ast.expressions.graph;

import java.util.Optional;

import ast.expressions.GraphExpression;
import ast.patterns.PathPattern;
import ast.patterns.QualifiedPathPattern;
import enums.EvaluationMode;

public class GraphExistsExpression implements GraphExpression {
    PathPattern pathPattern;

    public GraphExistsExpression(PathPattern pathPattern)
    {
        this.pathPattern = pathPattern;
    }

    public PathPattern pathPattern()
    {
        return pathPattern;
    }

    public QualifiedPathPattern qualifiedPathPattern()
    {
        return new QualifiedPathPattern(Optional.empty(), EvaluationMode.WALK, pathPattern);
    }
}
