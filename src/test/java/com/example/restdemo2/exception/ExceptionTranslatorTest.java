package com.example.restdemo2.exception;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.service.PersonService;
import com.example.restdemo2.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest
class ExceptionTranslatorTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PersonService personService;

    @MockBean
    TaskService taskService;

    @Test
    void testMethodArgumentNotValidException() throws Exception {
        String personJson = objectMapper.writeValueAsString(new Person());

        String result = mockMvc.perform(put("/api_v1/person/update").content(personJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String expectedMessage = "Name must not be blank.";

        Map map = objectMapper.readValue(result, Map.class);
        String actualMessage = ((Map) map.get("errors")).get("name").toString();

        Assertions.assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    void testBindException() throws Exception {
        String personJson = objectMapper.writeValueAsString(new Person());

        String result = mockMvc.perform(post("/api_v1/person/person")
                .param("name", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn().getResponse().getContentAsString();

        String expectedMessage = "Name must not be blank.";

        Map map = objectMapper.readValue(result, Map.class);
        String actualMessage = ((Map) map.get("errors")).get("name").toString();

        Assertions.assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}