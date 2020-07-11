package com.example.restdemo2.endpoint;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.dto.PersonDTO;
import com.example.restdemo2.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonEndpoint.class)
class PersonEndpointTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @Autowired
    PersonService personServiceTest;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    //@Test
    void testFunction_getAllPeople() {
        Person person = new Person(1L, "Quoc Cuong", 23, 1500.0, new Date(), Person.Status.ACTIVE);
        Page<Person> personPage = personServiceTest.getAllPeople("", "", 1, 10, null);
        given(personService.getAllPeople(null, null, 1, 10, null)).willReturn(personPage);
    }

    @Test
    void testFunction_getPersonById() throws Exception {
        Person person = new Person(1L, "Quoc Cuong", 23, 1500.0, new Date(), Person.Status.ACTIVE);
        given(personService.getOne(1L)).willReturn(person);

        MvcResult mvcResult = mockMvc.perform(get("/api_v1/person/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        String personDtoJson = mvcResult.getResponse().getContentAsString();
        PersonDTO personDTOMapper = objectMapper.readValue(personDtoJson, PersonDTO.class);
        assertThat(personDTOMapper.getName()).isEqualTo(person.getName());
    }

    @Test
    void testFunction_updatePerson() throws Exception {
        Person person = new Person(1L, "Quoc Cuong", 23, 3500.0, new Date(), Person.Status.ACTIVE);
        given(personService.updatePerson(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));

        MvcResult mvcResult = mockMvc.perform(put("/api_v1/person/update").content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.salary", is(3500.0)))
                .andReturn();

        String personDtoUpdate = mvcResult.getResponse().getContentAsString();
        PersonDTO personDTO = objectMapper.readValue(personDtoUpdate, PersonDTO.class);
        assertThat(personDTO.getSalary()).isEqualTo(person.getSalary());
    }
}
