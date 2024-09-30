package com.nodo.crud.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    HashMap<String, Object> data;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    public List<Person> getPerson(){
        return this.personRepository.findAll();
    }

    public ResponseEntity<Object> savePerson(Person person){
        data = new HashMap<>();

        // Verify if the person already exists
        if (person.getId() != null){
            boolean existId = this.personRepository.existsById(person.getId());
            if (existId){
                data.put("error", true);
                data.put("message", String.format("A person with the id %d already exist", person.getId()));
                return new ResponseEntity<>(
                        data,
                        HttpStatus.CONFLICT
                );
            }
        }

        // Verify if the person with that dni exists
        Optional<Person> findPerson = personRepository.findPersonByDni(person.getDni());
        if (findPerson.isPresent()){
            data.put("error", true);
            data.put("message", String.format("A person with the dni %d already exist", person.getDni()));
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        personRepository.save(person);
        data.put("data", person);
        data.put("message", "Person saved successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updatePerson(Person person){
        data = new HashMap<>();

        if (person.getId() == null){
            data.put("error", true);
            data.put("message", "The id person is mandatory");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }

        // Verify if the id exists
        boolean idExists = personRepository.existsById(person.getId());
        if(!idExists){
            data.put("error", true);
            data.put("message", String.format("The id %d doesn't exists", person.getId()));
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }

        personRepository.save(person);
        data.put("data", person);
        data.put("message", "Person updated successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deletePerson(Long id){
        data = new HashMap<>();
        boolean idExists = this.personRepository.existsById(id);

        if(!idExists){
            data.put("error", true);
            data.put("message", String.format("The id %d doesn't exist", id));
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }

        this.personRepository.deleteById(id);
        data.put("message", String.format("Person with id %d was deleted", id));
        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }
}
