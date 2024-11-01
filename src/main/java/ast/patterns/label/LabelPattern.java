package ast.patterns.label;

import java.util.List;

public abstract sealed class LabelPattern 
    permits WildcardLabel, LabelExpression {

        public static List<String> matchedLabels(LabelPattern pattern)
        {
            if (pattern instanceof WildcardLabel)
            {
                return List.of();
            }
            else if (pattern instanceof Label)
            {
                return List.of(((Label) pattern).value);
            }
            else if (pattern instanceof BinaryLabelExpression)
            {
                throw new RuntimeException("Binary Label Expressions Unsupported");
            }
            else 
            {
                throw new RuntimeException("Unsupported Label Expression");
            }
        } 
}
