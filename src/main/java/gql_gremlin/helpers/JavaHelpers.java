package gql_gremlin.helpers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaHelpers {
    public static <T> HashSet<T> intersection(Set<T> a, Set<T> b)
    {
        HashSet<T> c = new HashSet<T>(a);

        c.retainAll(b);
        return c;
    }

    public static <T> HashSet<T> subtraction(Set<T> a, Set<T> b)
    {
        HashSet<T> c = new HashSet<T>(a);
        c.removeAll(b);

        return c;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] safeToArray(List<T> list)
    {
        if (list == null)
        {
            Object[] empty = new Object[0];
            return (T[]) empty;
        }
        else {
            return (T[]) list.toArray();
        }
    }
}
