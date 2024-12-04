# Neo4J & Mysql Example

2024-12-04 lorne

本项目是搭建了一个兼容关系型数据库与图数据库的框架，采用springboot3.4.0开发，依赖springboot-jpa + springboot-neo4j + springboot-web实现。
本实例支持两种数据库的操作，分别是Neo4J和Mysql。
通过配置不同包路径下事务扫描路径，实现两个数据库的事务管理。

## 依赖服务
1. jdk17+
2. maven 3.9+
3. docker-compose v2.30+
4. idea

## 框架介绍

### Neo4J
```java
@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(
        basePackages = "com.codingapi.db.neo4j", // 扫描 Neo4j 仓库
        transactionManagerRef = "neo4jTransactionManager" // 指定事务管理器
)
public class Neo4jConfiguration {

    @Bean
    public PlatformTransactionManager neo4jTransactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }

    @Bean
    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
    }
}

```

### Mysql
```java

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.codingapi.db.mysql") // 扫描 JPA 实体类
@EnableJpaRepositories(
        basePackages = "com.codingapi.db.mysql", // 扫描 JPA 仓库
        transactionManagerRef = "jpaTransactionManager" // 指定事务管理器
)
public class MysqlConfiguration {

    @Bean
    @Primary
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

```

### Neo4j 的使用

@Node 节点的使用
```java

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

```

@RelationshipProperties 关系属性的使用
```java
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
```


Neo4jRepository 的使用
```java
public interface PersonRepository extends Neo4jRepository<Person,String> {

    @Query("MATCH (n) DETACH DELETE n")
    void clearAll();

    Person getPersonByName(String name);

    Person getPersonById(String id);

    @Query("MATCH (n:Person)-[r:KNOWS]->(t:Person) WHERE n.id = $sourceId and t.id = $targetId DELETE r")
    void removeKnowsRelationshipByPersonId(String sourceId,String targetId);
}

```

注意： 直接将Person对象下的knowsRelationships删除，然后通过save函数保存时，不生效，为此修改成了通过cypher语句删除关系。

无论是Neo4jRepository 还是 JpaRepository 都都是继承了CrudRepository、PagingAndSortingRepository、QueryByExampleExecutor，因此在使用时两者差距并不大。

只是Neo4j与JPA下的对Entity定义的注解存在差异。


## 运行说明

1. 运行scripts下的`sh reinstall.sh`,通过docker-compose 按照mysql与neo4j的数据库
2. 运行项目Application
3. 测试可以通过postman工具对ExampleController下的接口测试，也可以通过idea工具执行api下的generated-requests.http文件执行接口测试。

neo4j的web管理界面：http://localhost:7474/browser/
neo4j与mysql的账号密码见docker-compose.yaml 的配置

## 参考资源
1. [spring-data-neo4j](https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/)
2. [neo4j教程](https://www.w3cschool.cn/neo4j/delete.html)

