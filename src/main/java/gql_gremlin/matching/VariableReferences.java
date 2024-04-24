package gql_gremlin.matching;
import java.util.List;

public record VariableReferences(
    List<Integer> restrictedReferenceIndices,
    UnrestrictedReference unrestrictedReference
) {
    enum UnrestrictedReference {
        NoReference,
        SomeReference,
        MultipleReferences
    }

    VariableReferences(List<Integer> restrictedReferenceIndices)
    {
        this(restrictedReferenceIndices, UnrestrictedReference.NoReference);
    }

    VariableReferences addUnrestrictedReference()
    {
        if (unrestrictedReference == UnrestrictedReference.MultipleReferences)
        {
            return this;
        }
        else if (unrestrictedReference == UnrestrictedReference.SomeReference)
        {
            return new VariableReferences(restrictedReferenceIndices, UnrestrictedReference.MultipleReferences);
        }
        else if (unrestrictedReference == UnrestrictedReference.NoReference)
        {
            return new VariableReferences(restrictedReferenceIndices, UnrestrictedReference.SomeReference);
        }
        else {
            throw new IllegalStateException("Bad Variable References");
        }
    }

    public boolean intersection()
    {
        return restrictedReferenceIndices.size() > 0 || unrestrictedReference == UnrestrictedReference.MultipleReferences;
    }

}
