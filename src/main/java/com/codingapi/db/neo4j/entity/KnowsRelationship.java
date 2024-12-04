package com.codingapi.db.neo4j.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

/**
 *  KnowsRelationship 需要设置 @RelationshipProperties
 *  不需要添加 KnowsRelationship 对象的 Repository对象
 */
@Setter
@Getter
@RelationshipProperties
@NoArgsConstructor
public class KnowsRelationship {

    /**
     * id
     *  必须设置 @Id 和 @GeneratedValue
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     *  relationship的属性
     */
    private int since;

    /**
     * 目标节点 （只能存在一个@TargetNode）
     */
    @TargetNode
    private Person target;


    public KnowsRelationship(int since, Person target) {
        this.since = since;
        this.target = target;
    }
}
