- Implement LSQB queries
    - Missing:
        - Basic (q1-7)
            - Undirected Edges
            - Count function
            - Simple Negation (of variables) (q5, q6)
                - i.e. `tag1 != tag2`, `person1 != person3`
        - Complex (q8-9)
            - Negation
                - i.e. where `!(comment -[:hastag]-> tag)`

- implement graph selection override
    - so don't have to write FROM lsqb_0.1 and can hotswap graphs easily.

# Implementing Undirected Edges

- Tinkerpop doesn't natively support undirected edges
    - Two main ways to approach undirected edges
        - Elimination
            - All datasets with undirected edges have those edges transformed into two edges so we go from {s,t} to (s,t) and (t,s).
            - Queries are transformed either
                - at the source level, so that the undirected edge becomes just an outgoing edge, or both edges are checked for
                    - This would be done manually to test queries
                - at the compiler level, an undirected edge just refers to a check for an identical outgoing and incomingedge
                    - This would be done automatically, and allow the support of undirected edges, under a new "mutual relation" semantic
                        - Any identical edges e, e' between the same nodes with reversed direction are considered a single "undirected edge", as well as two independent outgoing and incoming edges.
                - With a schema that could identify a label as always undirected, the compiler could omit the double check
        - Support
            - Undirected edges are represented as a single arbitrary directed edge with a special undirected property/label

Choice:
    - double relation semantics
        - Implementation: Sub-match statement

// if it's unclear from cond whether a matching edge must be undirected
```
V().
match(
    as("a").outE().<cond>().inV().as("b"),
    as("a").inE().<cond>().outV().as("b")
).
select("b")
```

// if it's clear from cond a matching edge must be undirected
```
V().outE().<cond>().inV()
```

// if we are applying the

As such in our GQL

* `-[]-` is a mutual direction.
*

# Implementing count

- simple as cuz
- actually not so simple

when we want to count (or in anyway aggergate) a particular variable we need to use aggregate rather than as.

as has a canonical value for each traverser, while aggregate builds up a list shared by all traversers.

- aggregate has two modes
    - Global:
        - Add to the list by key
        - Wait for all other traversers to reach this point and add their element to the list
    - Local:
        - Add to the list by key
        - Continue
If using aggregate local and select, we must preceed the select with a barrier, to ensure all elements have been added to the list during the select step.

However just adding
`COUNT(*)`
is very simple
- what we'll do for now


# Problem

Because semantics of a "path" is different between GQL and Cypher it is not generally possible to compare results

- Cypher prohibits repeated edges in all cases
- GQL doesn't

However, because we have access to the schema and query we can determine whether the result may differ directly, on a query by query basis.

## Query Status

- q1: safe, no repeated edge types, so no edges will be repeated by either
- q2: likely safe, hasCreator is repeated, but the heads are two person nodes connected by a knows edge, unlikely for a person to "know" themself, so unlikely for a non-trail to be valid
- q3: not safe, if 3 people know each other and in the same city, three isPartOf edges could be duplicated, going from that city to its country
    - need to ensure distinctness of cities
        - Per schema, each city is in one country, so given that no person know's themself and each city is distinct we will not match non-trails
- q3 actually: is safe
    - We actually see the cypher query uses multiple match statements to allow isPartOf edges to overlap, we can leave the query as is
- q4: safe, no repeated edge types
- q5: safe, duplicate hasTag edges come from different sources (labels are different)
- q6: safe, duplicated knows edges made up of different pairs
- q7: safe, no repeated edge type

