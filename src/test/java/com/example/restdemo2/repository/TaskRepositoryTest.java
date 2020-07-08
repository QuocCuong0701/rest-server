package com.example.restdemo2.repository;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PersonRepository personRepository;

    @Test
    public void whenSaveTaskThenReturnTask() {
        Person person = new Person("Hà Lan", 21, 1500.0, new Date(), Person.Status.ACTIVE);
        personRepository.save(person);

        Task task = new Task(9L, "đi chợ", "Mua phải tiết kiệm", 5, person);
        taskRepository.save(task);

        Task taskReturn = taskRepository.getOne(1L);
        Assertions.assertThat(taskReturn.getTitle()).isEqualTo(task.getTitle());
    }
}