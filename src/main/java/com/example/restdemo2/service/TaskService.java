package com.example.restdemo2.service;

import com.example.restdemo2.domain.Task;
import com.example.restdemo2.dto.TaskDTO;
import com.example.restdemo2.endpoint.rest.RESTResponse;
import com.example.restdemo2.repository.TaskRepository;
import com.example.restdemo2.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity<Object> getALL(Long id) {
        TaskSpecification specification = TaskSpecification.spec();

        Optional.ofNullable(id).ifPresent(s -> specification.byPersonId(id));

        List<Task> tasks = taskRepository.findAll(specification.build());

        return new ResponseEntity<>(
                RESTResponse.Builder()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("Lấy danh sách công việc thành công!")
                        .setDatas(tasks.stream().map(TaskDTO::new).collect(Collectors.toList()))
                        .build()
                , HttpStatus.OK
        );
    }

    public List<Task> getAllTasks(long personId) {
        TaskSpecification specification = TaskSpecification.spec();

        Optional.of(personId).ifPresent(s -> specification.byPersonId(personId));

        return taskRepository.findAll(specification.build());
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

}
