package com.example.restdemo2.dto;
import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Data
public class TaskDTO {
    public static ModelMapper modelMapper = new ModelMapper();
    private String title;
    private String description;
    private String image;
    private Long personId;
    private Person person;

    public TaskDTO(Task task) {
        task.setPerson(new Person());
        modelMapper.map(task, this);
    }
}
