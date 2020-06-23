package com.example.restdemo2.endpoint;

import com.example.restdemo2.domain.Task;
import com.example.restdemo2.dto.TaskDTO;
import com.example.restdemo2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api_v1/task")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class TaskEndpoint {
    @Autowired
    TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> task(@PathVariable("id") Long personId) {
        return taskService.getALL(personId);
    }

    /*@GetMapping("/{id}")
    public List<TaskDTO> findTask(@PathVariable("id") Long id){
        return taskService.getALL()
    }*/

    @PostMapping("/save")
    public TaskDTO saveTask(@RequestBody Task task) {
        return new TaskDTO(taskService.save(task));
    }
}
