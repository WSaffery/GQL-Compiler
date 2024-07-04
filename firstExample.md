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


## Running the LSQB Queries

Launch a database, in this case we'll use Janus Graph.
```
cd <janusgraph install dir>
bin/janusgraph-server.sh console
```

Ensure you update `conf/gremlin-server/gremlin-server.yaml` so that your evaluation timeout is larger than `30000`, and you use a persistent backend such as the <>
by setting the graph key in graphs to the appropriate config file.

```
graphs {
	graph: conf/janusgraph-berkeleyje.properties
}
```

Load the LSQB graph into the database if it isn't already.
```
mvn exec:java -Dexec.mainClass="NewRemoteDbApp" -e -Dexec.args="-conf conf/remote-objects.yaml -mode load -graph lsqb01"
```

Then execute q0 to sanity check the data load.
```
mvn exec:java -Dexec.mainClass="RunRemoteDbApp" -e -Dexec.args="-conf conf/remote-objects.yaml -query gql/lsqb_patched/q0.gql"
```

You may then execute any (available) lsqb query in the same manner.

Correct and tested
- q0
- q1
- q2
