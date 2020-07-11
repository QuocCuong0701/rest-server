package com.example.restdemo2.service;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.repository.PersonRepository;
import com.example.restdemo2.specification.PersonSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @MockBean
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testFunction_getAllPeople() throws JsonProcessingException {
        /*String pagePerson = "{\"pagination\":{\"page\":1,\"limit\":10,\"totalPages\":1,\"totalItems\":1},\"data\":[{\"id\":1,\"name\":\"Quoc Cuong\",\"age\":23,\"salary\":1500.0,\"salaryFormat\":\"34.500.000 đ\",\"dob\":884131200000,\"dobFormat\":\"07/01/1998\",\"statusStr\":\"Đã kích hoạt\",\"status\":\"ACTIVE\",\"tasks\":[{\"id\":73,\"title\":\"clean the house\",\"description\":\"by hands\",\"priority\":5,\"person\":{\"id\":1,\"name\":\"Quoc Cuong\",\"age\":23,\"salary\":1500.0,\"dob\":884131200000,\"status\":\"ACTIVE\"}},{\"id\":2,\"title\":\"quét nhà\",\"description\":\"Nhà phải sạch\",\"priority\":4,\"person\":{\"id\":1,\"name\":\"Quoc Cuong\",\"age\":23,\"salary\":1500.0,\"dob\":884131200000,\"status\":\"ACTIVE\"}},{\"id\":6,\"title\":\"tưới cây\",\"description\":\"Cây xanh tươi\",\"priority\":3,\"person\":{\"id\":1,\"name\":\"Quoc Cuong\",\"age\":23,\"salary\":1500.0,\"dob\":884131200000,\"status\":\"ACTIVE\"}},{\"id\":90,\"title\":\"Code ra Facebook\",\"description\":\"trong 1 tháng\",\"priority\":1,\"person\":{\"id\":1,\"name\":\"Quoc Cuong\",\"age\":23,\"salary\":1500.0,\"dob\":884131200000,\"status\":\"ACTIVE\"}}],\"hasTask\":true}],\"message\":\"Lấy danh sách thành công!\",\"status\":200}";
        Page personJson = objectMapper.readValue(pagePerson, Page.class);*/
        PersonSpecification specification = PersonSpecification.spec();

        Optional.of("cuong").ifPresent(specification::byName);
        Optional.of("ACTIVE").ifPresent(s -> specification.byStatus(Person.Status.valueOf(s)));
        Optional.of(1L).ifPresent(s -> specification.byId(1L));

        Page<Person> personPage = personRepository.findAll(specification.build(), PageRequest.of(1, 10));
        personRepository.findAll(specification.build(), PageRequest.of(1, 10));
        Page<Person> personPage1 = personService.getAllPeople("cuong","ACTIVE",1,10,1L);

    }

    @Test
    void testFunction_getOne() {
        Person person = new Person(1L, "Quoc Cuong", 23, 1500.0, new Date(), Person.Status.ACTIVE);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Person personFound = personService.getOne(1L);
        assertEquals(personFound, person);
    }

    @Test
    void testFunction_updatePerson() {
        Person person = new Person(1L, "Quoc Cuong", 23, 1500.0, new Date(), Person.Status.ACTIVE);
        when(personRepository.save(person)).thenReturn(person);

        Person personFound = personService.updatePerson(person);
        assertEquals(personFound, person);
    }
}