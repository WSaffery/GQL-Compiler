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

    public boolean isGroup(String name)
    {
        return variableMap.get(name).group();
    }

    public boolean variableExists(String name)
    {
        return variableMap.containsKey(name);
    }

    public void addVariable(String name, VariableType type)
    {
        addVariable(name, type, false);
    }

    public void addVariable(String name, VariableType type, boolean group)
    {
        if (variableMap.containsKey(name) && 
            variableMap.get(name).type() != type)
        {
            throw new SyntaxErrorException("Variable repeated with different types");
        }
        variableMap.put(name, new Variable(name, type, group));
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
