# run Janus

mvn exec:java -Dexec.mainClass="RemoteDbApp" -Dexec.args="-graphs c3 c4 g g1 g2 g3 g4 g5 g"
# tests loading and dropping each graph into the janus graph
