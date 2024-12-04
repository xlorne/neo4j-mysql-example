package com.codingapi.db.controller;

import com.codingapi.db.controller.pojo.ExampleCmd;
import com.codingapi.db.mysql.entity.User;
import com.codingapi.db.mysql.service.UserService;
import com.codingapi.db.neo4j.entity.Person;
import com.codingapi.db.neo4j.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/example")
@AllArgsConstructor
public class ExampleController {

    private final UserService userService;

    private final PersonService personService;

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/savePerson")
    public Person savePerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.users();
    }


    @GetMapping("/persons")
    public List<Person> persons() {
        return personService.persons();
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam Long id) {
        userService.delete(id);
    }

    @DeleteMapping("/deletePerson")
    public void deletePerson(@RequestParam String id) {
        personService.delete(id);
    }

    @PostMapping("/addRelationship")
    public void addRelationship(@RequestBody ExampleCmd.AddRelationshipRequest request) {
        personService.addRelationship(request.getSourceId(), request.getTargetId(), request.getSince());
    }

    @DeleteMapping("/removeRelationShip")
    public void removeRelationShip(@RequestBody ExampleCmd.RemoveRelationShipRequest request) {
        personService.removeRelationShip(request.getSourceId(), request.getRemoveId());
    }

    @DeleteMapping("/clearAll")
    public void clearAll(){
        personService.clearAll();
    }

}
