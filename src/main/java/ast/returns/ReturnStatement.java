package ast.returns;

import java.util.ArrayList;

import enums.SetQuantifier;

public record ReturnStatement(SetQuantifier setQuantifier, ArrayList<ReturnItem> returnItems)
{
    public boolean returnAll()
    {
        return returnItems.size() == 1 && returnItems.get(0) instanceof Asterisk;
    }

    public boolean returnNone()
    {
        return returnItems.size() == 1 && returnItems.get(0) instanceof CountAsterisk;
    }

    public ArrayList<String> variableNames()
    {
        ArrayList<String> names = new ArrayList<>();
        for (ReturnItem item : returnItems)
        {
            if (item instanceof ReturnExpression)
            {
                ReturnExpression returnExpression = (ReturnExpression) item;
                names.addAll(returnExpression.expression().referencedVariables());
            }
        }
        return names;
    }
}