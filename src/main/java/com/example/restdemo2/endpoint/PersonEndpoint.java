package com.example.restdemo2.endpoint;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.dto.PersonDTO;
import com.example.restdemo2.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping(value = "/api_v1/person")
@CrossOrigin(value = "*", allowedHeaders = "*")
@Validated
public class PersonEndpoint {

    @Autowired
    PersonService personService;

    @GetMapping
    public ResponseEntity<Object> persons(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "personId", required = false) Long personId
    ) {
        return personService.getAll(keyword, status, page, limit, personId);
    }

    @GetMapping("/{id}")
    public PersonDTO person(@PathVariable("id") Long id) {
        return new PersonDTO(personService.getOne(id));
    }

    @PostMapping("/update")
    public PersonDTO updatePerson(@RequestBody Person person) {
        return new PersonDTO(personService.updatePerson(person));
    }

    // Test Validation
    @GetMapping("/person/{id}")
    public Person getById(@Min(2) @PathVariable("id") Long id) {
        return new Person("Cuong", 23);
    }

    @PostMapping("/person")
    public Person create(@RequestBody @Validated Person person) {
        return person;
    }
    // End Test Validation
}
