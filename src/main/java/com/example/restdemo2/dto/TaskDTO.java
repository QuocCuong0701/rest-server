package com.example.restdemo2.dto;
import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {
    public static ModelMapper modelMapper = new ModelMapper();
    private Long id;
    private String title;
    private String description;
    private int priority;
    private String priorityName;
    private String classTable;
    private Long personId;

    public TaskDTO(Task task) {
        BeanUtils.copyProperties(task, this);
        this.personId = task.getPerson().getId();
        task.setPerson(task.getPerson());
        Task.Priority priority = Task.Priority.getPriorityByCode(task.getPriority());
        this.priorityName = priority.getName();
        this.classTable = priority.getClassTable();
    }
}
