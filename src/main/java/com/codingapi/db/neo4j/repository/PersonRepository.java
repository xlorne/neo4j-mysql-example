package com.codingapi.db.neo4j.repository;

import com.codingapi.db.neo4j.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface PersonRepository extends Neo4jRepository<Person,String> {

    @Query("MATCH (n) DETACH DELETE n")
    void clearAll();

    Person getPersonByName(String name);

    Person getPersonById(String id);

    @Query("MATCH (n:Person)-[r:KNOWS]->(t:Person) WHERE n.id = $sourceId and t.id = $targetId DELETE r")
    void removeKnowsRelationshipByPersonId(String sourceId,String targetId);
}
