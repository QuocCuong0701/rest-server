package com.example.restdemo2.repository;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PersonRepository personRepository;

    @Test
    public void whenSaveTaskThenReturnTask() {
        Task task = new Task("đi chợ", "Mua phải tiết kiệm", 5, new Person(5L));
        Task taskReturn = testEntityManager.persistAndFlush(task);
        Assertions.assertThat(taskReturn.getTitle()).isEqualTo(task.getTitle());
    }

    @Test
    public void testFunction_getAllByPersonId() {
        List<Task> tasks = taskRepository.getAllByPersonId(2L);
        Assertions.assertThat(tasks).isNotNull();
    }
}