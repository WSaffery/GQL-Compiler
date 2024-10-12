package ast.expressions.graph;

import java.util.ArrayList;
import java.util.List;
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

    public List<String> referencedVariables()
    {
        List<String> names = new ArrayList<>();
        for (Optional<String> name : pathPattern.variableSequence())
        {
            if (name.isPresent())
            {
                names.add(name.get());
            }
        }
        return names;
    }
}
