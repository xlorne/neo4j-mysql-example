package com.codingapi.db;

import com.codingapi.db.mysql.entity.User;
import com.codingapi.db.mysql.service.UserService;
import com.codingapi.db.neo4j.entity.Person;
import com.codingapi.db.neo4j.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Rollback(value = false)
class ExampleApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() {

        User user = new User();
        user.setName("lorne");

        userService.save(user);

        Person person = new Person();
        person.setName("lorne");

        personService.save(person);

    }

}
