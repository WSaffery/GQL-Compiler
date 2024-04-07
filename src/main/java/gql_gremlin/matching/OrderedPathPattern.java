package gql_gremlin.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record OrderedPathPattern(Optional<String> variableName, List<OrderedElementPattern> pathSequence) 
{
    OrderedPathPattern(Optional<String> variableName)
    {
        this(variableName, new ArrayList<>());
    }

    boolean captured()
    {
        return variableName.isPresent();
    }
};
