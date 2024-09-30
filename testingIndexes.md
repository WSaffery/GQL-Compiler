First run query one, without compile times

Compile command

```
mvn exec:java -Dexec.mainClass="GqlGremlinScriptApp" -Dexec.args="gql/lsqb_optimised/q1.gql"
```

Run command
```
mvn exec:java -Dexec.mainClass="RunRemoteDbApp" -Dexec.args="-language gremlin -query gql/lsqb_optimised/q1.gql.gremlin"
```

Then reload data with properties
```
mvn exec:java -Dexec.mainClass="RemoteDbApp" -Dexec.args="-mode drop"
mvn exec:java -Dexec.mainClass="RemoteDbApp" -Dexec.args="-mode load -graph lsqb01"
```

Then run the property indexed query

