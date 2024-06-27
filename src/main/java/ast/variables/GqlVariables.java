package ast.variables;

import java.util.Collection;
import java.util.HashMap;

import exceptions.SyntaxErrorException;

public class GqlVariables {
    private HashMap<String, Variable> variableMap;

    public GqlVariables()
    {
        variableMap = new HashMap<>();
    }

    public void addVariable(String name, VariableType type)
    {
        if (variableMap.containsKey(name) && 
            variableMap.get(name).type() != type)
        {
            throw new SyntaxErrorException("Variable repeated with different types");
        }
        variableMap.put(name, new Variable(name, type));
    }

    public Collection<Variable> getVariables()
    {
        return variableMap.values();
    }

    public Variable getVariable(String name)
    {
        return variableMap.get(name);
    }

    public VariableType getVariableType(String name)
    {
        return variableMap.get(name).type();
    }
}
