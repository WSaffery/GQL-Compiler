package gql_gremlin.matching;

import java.util.HashMap;
import java.util.Optional;

import ast.expressions.Value;
import ast.patterns.ElementPattern;
import ast.patterns.label.LabelExpression;
import gql_gremlin.helpers.VariableOccurenceCounter;

// our element pattern is "preceded" if 
// * it is preceeded by a pattern outside the ending match statement with the same variable name
// in this case the compiler ought to
// * prior to the match statement use select if starting, and where otherwise
// * in the match step use where in both cases 
public record OrderedElementPattern(ElementPattern pattern, Optional<VariableOccurenceCounter> variableData, boolean preceded) 
{
    public Optional<String> variableName() { return pattern.variableName(); };
    public LabelExpression labelExpression() { return pattern.labelExpression(); };
    public HashMap<String, Value> properties() { return pattern.properties(); };

    boolean intersection()
    {
        return variableData.isPresent() && variableData.get().intersection();
    }
}
