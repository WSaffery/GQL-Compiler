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

As such in our GQL

* `-[]-` is a mutual direction.
*

# Implementing count

- simple as cuz
