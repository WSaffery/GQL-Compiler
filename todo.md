- Implement LSQB queries
    - Missing:
        - Basic (q1-7)
            - Undirected Edges
            - Count function
        - Complex (q8-9)
            - Negation

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

