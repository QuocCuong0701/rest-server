package com.example.restdemo2.service;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import com.example.restdemo2.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskServiceTest {

    @MockBean
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Test
    void testGetTaskByPersonId() {
        List<Task> tasks = Arrays.asList(new Task(9L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(5L)),
                new Task(81L, "buy me a cup of coffee", "some sugar with a lot of ice", 4, new Person(5L)));

        Mockito.when(taskRepository.getAllByPersonId(5L)).thenReturn(tasks);

        List<Task> taskResult = taskService.taskList(5L);
        assertEquals(tasks, taskResult);
    }
}