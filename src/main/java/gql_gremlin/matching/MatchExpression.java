package gql_gremlin.matching;

import java.util.List;
import java.util.Optional;

import ast.expressions.Expression;
import ast.patterns.QualifiedPathPattern;

public class MatchExpression {
    public boolean isMandatory;
    public Optional<Expression> whereClause;
    public List<QualifiedPathPattern> pathPatterns;

    public MatchExpression(boolean isMandatory, Optional<Expression> whereClause, List<QualifiedPathPattern> pathPatterns)
    {
        this.isMandatory = isMandatory;
        this.whereClause = whereClause;
        this.pathPatterns = pathPatterns;
    }

    public String toString()
    {
        String str = "Is Mandatory:" + isMandatory + ", whereClause" + whereClause;
        int i = 0;
        for (QualifiedPathPattern pathPattern : pathPatterns)
        {
            str += ", pattern " + i++ + ": [" + pathPattern + "]";
        }
        return str;
    }
}
