# GQL Compiler

A [GQL](https://www.gqlstandards.org/home) to [Gremlin](https://tinkerpop.apache.org/docs/3.4.4/reference/#intro) transpiler written by William Saffery

This project was initially derived from Olaf Morra's [GQL Parser](https://github.com/OlofMorra/GQL-parser), but has since significantly diverged from it.
Some parts from GQL Parser remain in use including parts of the ANTLR grammar and the visitors used for transforming it, and some of the code for handling test datasets.

If you're marking my thesis you might be interested in the original [thesis branch](https://github.com/WSaffery/GQL-Compiler/tree/thesis) although I wouldn't recommend looking at it too much, it's only there for posterity.

# Standout Features

- Compiles to static Gremlin traversals (i.e. no match step, which we've found in practice is slow).
- Provides an optional traversal (join) order optimiser, with a tool for generating cardinality summaries of any Gremlin database

# Standout Limitations

- Somewhat limited features implemented
	- Focused read-only subset of GQL
- Optimiser only properly supports labelled subgraph queries, as benchmarked in the [LSQB](https://dl.acm.org/doi/pdf/10.1145/3461837.3464516)

# GQL Features

- Graph Pattern Matching
	- Inline label predicates (including disjunctions) (i.e. `(a: Label1 | Label2)`, and property predicates (i.e. `(a: Label1 | Label2 {v : 1)`.
- Bounded iteration of parenthesised paths (which use `[` and`]` not `(` and `)` right now).
- Acyclic path pattern mode
- Returning named elements, named groups, named element's properties (either individually selected \[i.e. `<name>.<value>`]  or all as a map with the non-standard `<name>.*` syntax), or a single global match count (`COUNT(*)`).
- `WHERE` clause with graphical predicates (i.e. `WHERE NOT (a)->(b)` and property predicates (i.e. `WHERE a.v > 1`).

# Setup

- Install maven
- Install antlr4 dependencies
- For Fedora antlr4-maven-plugin covers everything
	- Targeting latest Fedora antlr4 version over development period (2024)
	- This could change if there's a significant breaking change
- Run `./setupAntlr.sh`
- Run `mvn install`
- Test your installation by running `./runApp.sh GqlGremlinApp -query gql/gql_tests/iter_collapse.gql`

This test runs the following query

```
FROM chain_graph
MATCH (start_node) [(a) -[]-> (b) -[]-> (c)] {1,3} (end_node)
RETURN start_node, a, b, c, end_node
```

On the following labelled "chain" graph

```
(a1)->(a2)->(a3)->(a4)->(a5)->(a6)->(a7)->(a8)->(a9)
```

With results

```
 start_node| a                    | b                    | c                    | end_node|
 v[a1]     | [v[a1]]              | [v[a2]]              | [v[a3]]              | v[a3]   |
 v[a1]     | [v[a1], v[a3]]       | [v[a2], v[a4]]       | [v[a3], v[a5]]       | v[a5]   |
 v[a1]     | [v[a1], v[a3], v[a5]]| [v[a2], v[a4], v[a6]]| [v[a3], v[a5], v[a7]]| v[a7]   |
 v[a2]     | [v[a2]]              | [v[a3]]              | [v[a4]]              | v[a4]   |
 v[a2]     | [v[a2], v[a4]]       | [v[a3], v[a5]]       | [v[a4], v[a6]]       | v[a6]   |
 v[a2]     | [v[a2], v[a4], v[a6]]| [v[a3], v[a5], v[a7]]| [v[a4], v[a6], v[a8]]| v[a8]   |
 v[a3]     | [v[a3]]              | [v[a4]]              | [v[a5]]              | v[a5]   |
 v[a3]     | [v[a3], v[a5]]       | [v[a4], v[a6]]       | [v[a5], v[a7]]       | v[a7]   |
 v[a3]     | [v[a3], v[a5], v[a7]]| [v[a4], v[a6], v[a8]]| [v[a5], v[a7], v[a9]]| v[a9]   |
 v[a4]     | [v[a4]]              | [v[a5]]              | [v[a6]]              | v[a6]   |
 v[a4]     | [v[a4], v[a6]]       | [v[a5], v[a7]]       | [v[a6], v[a8]]       | v[a8]   |
 v[a5]     | [v[a5]]              | [v[a6]]              | [v[a7]]              | v[a7]   |
 v[a5]     | [v[a5], v[a7]]       | [v[a6], v[a8]]       | [v[a7], v[a9]]       | v[a9]   |
 v[a6]     | [v[a6]]              | [v[a7]]              | [v[a8]]              | v[a8]   |
 v[a7]     | [v[a7]]              | [v[a8]]              | [v[a9]]              | v[a9]   |
```

You can see the corresponding Gremlin code by running `./runApp.sh GqlGremlinScriptApp -query gql/gql_tests/iter_collapse.gql`

# Usage

Add a `remote-objects.yaml` to the `conf` directory with properties filled out appropriately for a Gremlin system supporting [Gremlin Server](https://tinkerpop.apache.org/docs/3.4.4/reference/#gremlin-server).

The following apps interact with a live Gremlin database and take a `-conf` argument containing the path of your `remote-objects.yaml` file.

All apps that use a query take a `-query` argument specifying a query path relative to `src/test/queries`.

Running any application with `-help` shows all its arguments their type (singular, multiple, or flag) and defaults, but doesn't include further documentation.
All apps have more in-depth documentation of their arguments in their source.

- `GenerateSummaryApp`
	- Generates a summary of the cardinalities of nodes by label, and label triplets, in the database
	- Used in the optimiser, which can be enabled for any App that transpiles GQL to Gremlin using the arguments 
		`-summary <summary_file> -compiler optimising`
- `RunRemoteDbApp`
	- Executes a GQL (or Gremlin) query on the database
	- Takes an optional `-summary` argument specifying a summary file relative to `src/main/summaries` generated by `GenerateSummaryApp`
		- Such a file is used to automatically optimise the traversal order of the query specified by the generated Gremlin code
		- Only labels are included in counted cardinalities, this won't consider property predicates.		
- `RunLsqbApp`
	- Execute the LSQB using a GQL query set on the database
- `RunGremlinLsqbApp`
	- Execute the LSQB using a Gremlin query set on the database
 - `RemoteDbApp`
 	- Select a graph stored using Olaf Morra's JSON format in `src/test/resources/database`  using the `-graph` argument and set the `-mode` argument to perform the following
  		- `test`: load the graph to the database and then drop it
    		- `load`: load the graph to the database
      		- `drop`: drop all data from the database, ignores `-graph`     

The following apps provide useful functionality that doesn't interact with a live Gremlin database.

- `GqlGremlinApp`
	- Transpiles a GQL query and executes it on a local TinkerGraph database, may use the `FROM` clause to select a graph stored using Olaf Morra's JSON format in `src/test/resources/database` and load it into the database first.
- `GqlGremlinScriptApp`
	- Transpiles a GQL query, and then converts the Gremlin traversal object to a Gremlin script string (which is then output).
- `GqlGremlinOrderApp`
	- Outputs the ordering the optimiser would choose for a given GQL query and summary file.
	- Essentially a high level view of the "query plan".


# Running an App

run 

```
./runApp.sh <AppName> <Args>
```

or alternatively run

```
mvn exec:java -Dexec.mainClass="<AppName>" -e -Dexec.args="<Args>"
```

# Getting started

The above is a high level summary a more detailed but shorter and more focused setup and usage guide can be found in `docs/docs.md`
