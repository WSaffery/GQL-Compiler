# GQL Compiler

A GQL to Gremlin transpiler written by William Saffery

This project was initially derived from Olaf Morra's GQL Parser, but has since significantly diverged from it.
Some parts of the ANTLR grammar and the visitors used for transforming it from GQL Parser remain in use.


# Usage

Add a `remote-objects.yaml` to the `conf` directory with properties filled out appropriately for a Gremlin system supporting Gremlin Server.

The following apps interact with a live Gremlin database and take a `-conf` argument containing the path of your `remote-objects.yaml` file.

All apps that use a query take a `-query` argument specifying a query path relative to `src/test/queries`.

All apps have a documentation of their arguments in their source code, automatic help is out of scope for now.

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

The following apps provide useful functionality that doesn't interact with a live Gremlin database.

- `GqlGremlinApp`
	- Transpiles a GQL query and executes it on a local TinkerGraph database, may use the `FROM` clause to select a graph stored using Olaf Morra's JSON format in `src/test/resources/database` and load it into the database first.
- `GqlGremlinScriptApp`
	- Transpiles a GQL query, and then converts the Gremlin traversal object to a Gremlin script string (which is then output).
- `GqlGremlinOrderApp`
	- Outputs the ordering the optimiser would choose for a given GQL query and summary file.
	- Essentially a high level view of the "query plan".


