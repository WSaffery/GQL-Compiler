# First Example
Given graph `g`
```
(n1) -[e1]- (n2) -[e2]- (n3) =[e3]
```
note: edge e3 is a self loop on node n3.

Running 
```
FROM g
MATCH SIMPLE (x)~[]~(y)~[]~(z)
RETURN x, y, z
```

## Using the Gql Interpreter

Run `mvn exec:java -Dexec.mainClass="GqlApp" -e -Dexec.args="/gql/tests/path_patterns/path_simple.gql"`
Result is:
```
+--------------------+--------------------+--------------------+
| x                  | y                  | z                  |
+--------------------+--------------------+--------------------+
| {"identity": "n3"} | {"identity": "n2"} | {"identity": "n1"} |
| {"identity": "n2"} | {"identity": "n1"} | {"identity": "n2"} |
| {"identity": "n1"} | {"identity": "n2"} | {"identity": "n3"} |
| {"identity": "n2"} | {"identity": "n3"} | {"identity": "n2"} |
| {"identity": "n1"} | {"identity": "n2"} | {"identity": "n1"} |
| {"identity": "n3"} | {"identity": "n2"} | {"identity": "n3"} |
+--------------------+--------------------+--------------------+
```

## Using handwritten gremlin

Run `mvn exec:java -Dexec.mainClass="NewGremlinApp"`
Result is:
```
path[v[n1], v[n2], v[n3]]
path[v[n3], v[n2], v[n1]]
path[v[n1], v[n2], v[n1]]
path[v[n2], v[n3], v[n2]]
path[v[n2], v[n1], v[n2]]
path[v[n3], v[n2], v[n3]]
```
