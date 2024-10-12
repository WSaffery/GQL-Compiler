package ast.queries;

import java.util.ArrayList;
import java.util.Collection;

public class GqlBody {
    private ArrayList<GqlQuery> queries = new ArrayList<GqlQuery>();
    private ArrayList<QueryConjunctor> conjunctions = new ArrayList<QueryConjunctor>();

    public boolean isSingular()
    {
        return queries.size() == 1;
    }

    public int getQueryCount()
    {
        return queries.size();
    }

    public Collection<GqlQuery> queries()
    {
        return queries;
    }

    public Collection<QueryConjunctor> conjunctions()
    {
        return conjunctions;
    }

    public void addQuery(GqlQuery query)
    {
        queries.add(query);
    }

    public void addConjunctor(QueryConjunctor conjunctor)
    {
        conjunctions.add(conjunctor);
    }

    public GqlQuery getQuery(int index) 
    {
        return queries.get(index);
    }

    public QueryConjunctor getConjunctor(int index) 
    {
        return conjunctions.get(index);
    }
}
