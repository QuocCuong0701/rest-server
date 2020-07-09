package com.example.restdemo2.endpoint;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.service.PersonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonEndpoint.class)
class PersonEndpointTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @Autowired
    PersonService personServiceTest;

    //@Test
    void testFunction_getAllPeople() {
        Person person = new Person(1L, "Quoc Cuong", 23, 1500.0, new Date(), Person.Status.ACTIVE);
        Page<Person> personPage = personServiceTest.getAllPerson("", "", 1, 10, null);
        given(personService.getAllPerson(null, null, 1, 10, null)).willReturn(personPage);
    }

    @Test
    void testFunction_getPersonById() throws Exception {
        Person person = new Person(1L, "Quoc Cuong", 23, 1500.0, new Date(), Person.Status.ACTIVE);
        given(personService.getOne(1L)).willReturn(person);

        MvcResult mvcResult = mockMvc.perform(get("/api_v1/person/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        //Assertions.assertThat(mvcResult.getResponse())
    }
}