package com.example.restdemo2.dto;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {
    private static ModelMapper modelMapper = new ModelMapper();

    private Long id;
    @NotBlank(message = "{NotBlank.person.name}")
    private String name;
    private Integer age;
    private Double salary;
    private String salaryFormat;
    private Date dob;
    private String dobFormat;
    private String statusStr;
    private Person.Status status;
    private boolean isHasTask;

    private List<Task> tasks = new ArrayList<>();

    public PersonDTO(Person person) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/language", new Locale("vi"));
        modelMapper.map(person, this);
        this.statusStr = bundle.getString(this.status.name());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.dobFormat = formatter.format(person.getDob());
        this.salaryFormat = convertUSDToVND(this.salary);
        this.isHasTask = !this.tasks.isEmpty();
    }

    private String convertUSDToVND(double salary) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(salary * 23000);
    }

    public Person toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        Person person = new Person();
        modelMapper.map(this, person);
        return person;
    }
}
