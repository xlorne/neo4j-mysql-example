package com.codingapi.db.neo4j.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
@ToString
public class Person {

    @Id
    private String id;

    private String name;

    /**
     *  relationship 关系，当类型设置为 @RelationshipProperties 时，可以为Relationship 设置属性，
     *  只是设置 @Relationship 然后关联@Node对象时，那么添加的relationship就只是关系信息，没有关系的属性信息。
     */
    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private List<KnowsRelationship> knowsRelationships = new ArrayList<>();


    public void addKnowsRelationship(int since, Person person) {
        knowsRelationships.add(new KnowsRelationship(since, person));
    }


    public KnowsRelationship getKnowsRelationship(String targetId) {
        return knowsRelationships.stream().filter(knowsRelationship -> knowsRelationship.getTarget().getId().equals(targetId)).findFirst().orElse(null);
    }
}
