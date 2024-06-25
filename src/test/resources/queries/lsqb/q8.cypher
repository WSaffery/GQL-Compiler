MATCH (tag1:Tag)<-[:hasTag]-(message:Message)<-[:replyOf]-(comment:Comment)-[:hasTag]->(tag2:Tag)
WHERE NOT (comment)-[:HAS_TAG]->(tag1)
AND tag1 <> tag2
RETURN count(*) AS count