# Setup from zero

- Install maven
- Install antlr4 dependencies
    - For Fedora `antlr4-maven-plugin` covers everything
    - Targeting latest Fedora antlr4 version over development period (2024)
        - This could change if there's a significant breaking change 
- run setupAntlr.sh
- run `mvn clean install`
    - run `mvn clean install -Dmaven.test.skip=true` if you're on linux (fix pending)
- run `mvn exec:java -Dexec.mainClass="GqlApp" -e -Dexec.args="/gql/tests/conjunctions/union_all.gql"` to test the query in `src/test/resources/queries/gql/tests/conjunctions/union_all.gql`
    - this may not work despite a successful build if you skipped the antlr4 dependency


Notes on quirks of the current codebase for quick reference

# Antlr

- version originally used is out of date
- regenerated with updated version, and updated maven; currently working fine
- should keep in mind

# JsonDB

- Edges need to have isDirected as their last property
- key-value pairs can't have a space following their key and preceeding their colon
- The last key-value pair must always have no comma (no optional trailing allowed)
- Some of the databases in test break these rules (i.e. fullExampleGraph)


