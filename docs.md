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
- run `mvn exec:java -Dexec.mainClass="GqlGremlinApp" -e -Dexec.args="/gql/honours_tests/property_pattern.gql"` to test the query in `src/test/resources/queries/gql/honours_tests/property_pattern.gql`
    - this may not work despite a successful build if you skipped the antlr4 dependency
    - this will run using test data local to the repo, and an in-memory tinkergraph to interpret the generated gremlin

## Testing Remote Providers

### With Janus graph setup

- run to test loading and deleting test graph g
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e`

- run with basic graphs
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e -Dexec.args="-graphs c3 c4 g g1 g2 g3 g4 g5 g6"`
	
- run with lsqb 0.1 sf
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e -Dexec.args="-graphs lsqb_0.1"`
