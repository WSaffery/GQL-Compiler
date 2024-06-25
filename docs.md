# Docs

Commands for different tasks.

# Remote testing

## With Janus graph setup

- run with g
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e`

- run with basic graphs
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e -Dexec.args="-graphs c3 c4 g g1 g2 g3 g4 g5 g6"`
	
- run with lsqb 0.1 sf
	`mvn exec:java -Dexec.mainClass="RemoteDbApp" -e -Dexec.args="-graphs lsqb_0.1"`
