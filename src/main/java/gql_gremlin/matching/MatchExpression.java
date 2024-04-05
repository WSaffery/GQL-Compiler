package gql_gremlin.matching;

import java.util.List;
import java.util.Optional;

import ast.expressions.Expression;
import ast.patterns.QualifiedPathPattern;

public class MatchExpression {
    Optional<String> graphName;
    boolean isMandatory;
    public Optional<Expression> whereClause;
    public List<QualifiedPathPattern> pathPatterns;

    public MatchExpression(Optional<String> graphName, boolean isMandatory, Optional<Expression> whereClause, List<QualifiedPathPattern> pathPatterns)
    {
        this.graphName = graphName;
        this.isMandatory = isMandatory;
        this.whereClause = whereClause;
        this.pathPatterns = pathPatterns;
    }

    public String toString()
    {
        String str = "graphName: " + graphName + ", Is Mandatory:" + isMandatory + ", whereClause" + whereClause;
        int i = 0;
        for (QualifiedPathPattern pathPattern : pathPatterns)
        {
            str += ", pattern " + i++ + ": [" + pathPattern + "]";
        }
        return str;
    }
}
