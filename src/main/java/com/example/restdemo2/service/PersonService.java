package com.example.restdemo2.service;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.dto.PersonDTO;
import com.example.restdemo2.endpoint.rest.RESTResponse;
import com.example.restdemo2.endpoint.rest.RESTPagination;
import com.example.restdemo2.repository.PersonRepository;
import com.example.restdemo2.specification.PersonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public ResponseEntity<Object> getAll(String keyword, String status, int page, int limit, Long id) {
        PersonSpecification specification = PersonSpecification.spec();

        Optional.ofNullable(keyword).ifPresent(specification::byName);
        Optional.ofNullable(status).ifPresent(s -> specification.byStatus(Person.Status.valueOf(s)));
        Optional.ofNullable(id).ifPresent(s -> specification.byId(id));

        Page<Person> personPage = personRepository.findAll(specification.build(), PageRequest.of(page - 1, limit));

        return new ResponseEntity<>(
                RESTResponse.Builder()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("Lấy danh sách thành công!")
                        .setDatas(personPage.getContent().stream().map(PersonDTO::new).collect(Collectors.toList()))
                        .setPagination(new RESTPagination(page, limit, personPage.getTotalPages(), personPage.getTotalElements())).build()
                , HttpStatus.OK
        );
    }

    public Person getOne(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }
}
