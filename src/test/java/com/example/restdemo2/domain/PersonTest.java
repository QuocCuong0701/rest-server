package com.example.restdemo2.domain;

import com.example.restdemo2.service.PersonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class PersonTest {

    @MockBean
    private PersonService personService;
    @Mock
    private Person person;

    @Test
    void testPerson() {
        Set<Task> tasks = personService.getOne(1L).getTasks();
        Task task = new ArrayList<>(tasks).get(0);
        Mockito.when(task.getTitle().contains("clean")).thenReturn(true);
        Assertions.assertThat(tasks).isNull();
    }
}