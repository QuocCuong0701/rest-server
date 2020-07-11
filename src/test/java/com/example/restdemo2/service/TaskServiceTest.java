package com.example.restdemo2.service;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import com.example.restdemo2.repository.TaskRepository;
import com.example.restdemo2.specification.TaskSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {

    @MockBean
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Test
    void testFunction_getAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(9L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(1L)),
                new Task(81L, "buy me a cup of coffee", "some sugar with a lot of ice", 4, new Person(1L)));

        TaskSpecification specification = TaskSpecification.spec();
        Optional.of(1L).ifPresent(s -> specification.byPersonId(1L));

        when(taskRepository.findAll(specification.build())).thenReturn(tasks);

        List<Task> tasksResult = taskService.getAllTasks(1L);
        //Assertions.assertThat(tasksResult.size()).isEqualTo(tasks.size());
    }

    @Test
    void testFunction_taskList() {
        List<Task> tasks = Arrays.asList(new Task(9L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(5L)),
                new Task(81L, "buy me a cup of coffee", "some sugar with a lot of ice", 4, new Person(5L)));

        when(taskRepository.getAllByPersonId(5L)).thenReturn(tasks);

        List<Task> taskResult = taskService.taskList(5L);
        assertEquals(tasks, taskResult);
    }

    @Test
    void testFunction_saveTask() {
        Task task = new Task(9L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(5L));
        when(taskRepository.save(task)).thenReturn(task);

        Task taskFound = taskService.save(task);
        Assertions.assertThat(taskFound).isEqualTo(task);
    }
}