package com.codingapi.db.neo4j.service;

import com.codingapi.db.neo4j.entity.KnowsRelationship;
import com.codingapi.db.neo4j.entity.Person;
import com.codingapi.db.neo4j.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }

    public void delete(String id){
        personRepository.deleteById(id);
    }


    public void addRelationship(String sourceId,String targetId,int since){
        Person source = personRepository.findById(sourceId).orElse(null);
        Person target = personRepository.findById(targetId).orElse(null);

        if(source != null && target != null){
            source.addKnowsRelationship(since,target);
            personRepository.save(source);
        }
    }

    public void clearAll(){
        personRepository.clearAll();
    }

    public void removeRelationShip(String sourceId,String removeId){
        personRepository.removeKnowsRelationshipByPersonId(sourceId,removeId);
    }

    public List<Person> persons(){
        return personRepository.findAll();
    }
}
