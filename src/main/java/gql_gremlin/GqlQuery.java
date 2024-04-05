package gql_gremlin;

import java.util.ArrayList;
import ast.ReturnStatement;
import ast.MatchExpression;

public class GqlQuery {
    public ArrayList<MatchExpression> matchExpressions = new ArrayList<>();
    public ReturnStatement returnStatement;

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
