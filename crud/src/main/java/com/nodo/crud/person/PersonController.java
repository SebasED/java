package com.nodo.crud.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPerson(){
        return personService.getPerson();
    }

    @PostMapping
    public ResponseEntity<Object> savePerson(@RequestBody Person person){
        return this.personService.savePerson(person);
    }

    @PutMapping
    public ResponseEntity<Object> updatePerson(@RequestBody Person person){
        return this.personService.updatePerson(person);
    }

    @DeleteMapping(path = "{personId}")
    public ResponseEntity<Object> deletePerson(@PathVariable("personId") Long id){
        return this.personService.deletePerson(id);
    }

}
