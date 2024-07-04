Queries from LSQB paper.
- Based off Cypher queries given in paper

Changes:
- Removed multiple matches in q3, as far as I can tell the main purpose is to allow repeated edges but GQL is already homomorphic for all edges.
- Switched from optional match to {0,1} group in q7, optional match is in GQL but not planned to be included in current implementation

Todo:
- q8 and q9 require negation, currently left in cypher form

These queries have been "patched" so that TagClass is now TagCla, a necessary edit for now.