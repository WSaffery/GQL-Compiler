package gql_gremlin.helpers;

import ast.patterns.ElementPattern;
import ast.patterns.ParenPathPattern;
import ast.patterns.PathComponent;
import ast.patterns.QualifiedPathPattern;
import ast.patterns.label.BinaryLabelExpression;
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import exceptions.SemanticErrorException;

import static gql_gremlin.helpers.GremlinHelpers.*;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.and;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.hasLabel;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.or;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;

public class CompilerHelpers
{
    // ----
    // CURRENT
    // ----

    // left deep nesting of and operation to get around inability to access an array of graph traversals
    public static GraphTraversal<Object, Object> recurseAndOperands(List<LabelExpression> operands)
    {
        if (operands.size() == 1) return filterByLabelExpression(operands.get(0));
        return and(filterByLabelExpression(operands.get(0)), recurseAndOperands(operands.subList(1, operands.size())));
    }

    // left deep nesting of or operation to get around inability to access an array of graph traversals
    public static GraphTraversal<Object, Object> recurseOrOperands(List<LabelExpression> operands)
    {
        if (operands.size() == 1) return filterByLabelExpression(operands.get(0));
        return or(filterByLabelExpression(operands.get(0)), recurseAndOperands(operands.subList(1, operands.size())));
    }

    public static GraphTraversal<Object, Object> filterByLabelExpression(LabelExpression labelExpression)
    {
        if (labelExpression instanceof Label)
        {
            Label label = (Label) labelExpression;
            return hasLabel(label.getValue());
        }
        else if (labelExpression instanceof BinaryLabelExpression)
        {
            final BinaryLabelExpression binaryExpression = (BinaryLabelExpression) labelExpression;
            List<LabelExpression> operands = binaryExpression.operandExpressions();    
            // @SuppressWarnings("unchecked")
            // GraphTraversal<Object,Object>[] subTraversals = (GraphTraversal<Object,Object>[]) operands.stream().map((operand) -> filterByLabelExpression(operand)).toArray();

            switch (binaryExpression.operator())
            {
                case AND:                   
                    return recurseAndOperands(operands);

                case OR:
                    return recurseOrOperands(operands);
            }
        }
        throw new SemanticErrorException("Bad label expression");
    }

    // ---
    // CURRENTLY UNUSED    
    // ---
    // previously used in match compiler

    // reorders restricted path patterns so that for each of the contained connected components
    // its path patterns are contiguous in the resulting list and in the order of some dfs through the component
    //
    // i.e. [(a -> b), (d -> a), (b -> c), (c -> d)] becomes [(a -> b), (b -> c), (c -> d), (d -> a)]
    // note this function doesn't care about the direction of edges in patterns, assumption being querying is bidirectional
    public static List<QualifiedPathPattern> orderRestrictedPathPatterns(List<QualifiedPathPattern> restrictedPathPatterns)
    {
        ArrayList<QualifiedPathPattern> reorderedPathPatterns = new ArrayList<>();

        List<HashSet<String>> variablesList = 
            restrictedPathPatterns.stream().map(pathPattern -> pathPatternVariables(pathPattern)).toList();

        
        while (restrictedPathPatterns.size() > 0)
        {
            int i = restrictedPathPatterns.size()-1;

            while (i != -1) 
            {
                reorderedPathPatterns.add(restrictedPathPatterns.remove(i));
                Set<String> variables = variablesList.remove(i);
                i = -1;

                for (int j = 0; j < restrictedPathPatterns.size(); j++)
                {
                    Set<String> testVariables = variablesList.get(j);
                    if (intersects(variables, testVariables))
                    {
                        i = j;
                        break;
                    }
                }
            }
        }

        return reorderedPathPatterns;
    }
    
    public static Stream<String> pathPatternVariablesAllLayersStream(List<PathComponent> pathSequence)
    {
        return pathSequence.stream().flatMap(component -> {
            Stream<String> stream = null;
            if (component instanceof ParenPathPattern)
            {
                stream = pathPatternVariablesAllLayersStream(((ParenPathPattern) component).pathPattern().pathSequence());
            }
            else 
            {
                stream = Stream.ofNullable(((ElementPattern) component).variableName.orElse(null));
            }
            return stream;
        });
    }

    public static HashSet<String> pathPatternVariablesAllLayers(List<PathComponent> pathSequence)
    {
        return new HashSet<>(pathPatternVariablesAllLayersStream(pathSequence).toList());
    }

    public static HashSet<String> pathPatternVariables(List<PathComponent> pathSequence)
    {
        return new HashSet<>(pathSequence.stream().flatMap(component -> {
            Stream<String> stream = null;
            if (component instanceof ParenPathPattern)
            {
                stream = Stream.of();
            }
            else 
            {
                stream = Stream.ofNullable(((ElementPattern) component).variableName.orElse(null));
            }
            return stream;
        }).toList());
    }

    public static HashSet<String> pathPatternVariables(QualifiedPathPattern pathPattern)
    {
        return pathPatternVariables(pathPattern.pathPattern().pathSequence());
    }

}

