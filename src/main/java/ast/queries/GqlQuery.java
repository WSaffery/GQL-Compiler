package ast.queries;

import java.util.ArrayList;
import ast.returns.ReturnStatement;
import ast.variables.GqlVariables;
import gql_gremlin.matching.MatchExpression;

public class GqlQuery {
    public ArrayList<MatchExpression> matchExpressions = new ArrayList<>();
    public ReturnStatement returnStatement;
    public GqlVariables variables;

    public void print() 
    {
        int i = 0;
        for (MatchExpression expr : matchExpressions)
        {
            System.out.println("Match Expression " + i++ + ":");
            System.out.println(expr);
        }
        System.out.println(returnStatement);
    }
}
