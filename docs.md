# Docs

Commands for different tasks.

## Setup from zero

- Install maven
- Install antlr4 dependencies
    - For Fedora `antlr4-maven-plugin` covers everything
    - Targeting latest Fedora antlr4 version over development period (2024)
        - This could change if there's a significant breaking change 
- run setupAntlr.sh
- run `mvn clean install`
    - run `mvn clean install -Dmaven.test.skip=true` if you're on linux (fix pending)
- run `mvn exec:java -Dexec.mainClass="GqlGremlinApp" -e -Dexec.args="-query /gql/honours_tests/property_pattern.gql"` to test the query in `src/test/resources/queries/gql/honours_tests/property_pattern.gql`
    - this may not work despite a successful build if you skipped the antlr4 dependency
    - this will run using test data local to the repo, and an in-memory tinkergraph to interpret the generated gremlin

## Testing Remote Providers

### With Janus graph setup

- run to test loading and deleting test graph g
	`mvn exec:java -Dexec.mainClass="TestRemoteDbApp" -e`

- test load and drop with all basic graphs
	`mvn exec:java -Dexec.mainClass="TestRemoteDbApp" -e -Dexec.args="-graphs c3 c4 g g1 g2 g3 g4 g5 g6"`
	
- load lsqb 0.1 sf
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e -Dexec.args="-mode load -graph lsqb01"`


## Running LSQB

After loading lsqb check it's in the database

```
mvn exec:java -Dexec.mainClass="RunRemoteDbApp" -e -Dexec.args="-conf conf/remote-objects.yaml -query gql/lsqb_patched/q0.gql"
```

Then to run the full benchmark


```
mvn exec:java -Dexec.mainClass="RunLsqbApp" -e -Dexec.args="-conf conf/remote-objects.yaml -results results/results.csv"
```
