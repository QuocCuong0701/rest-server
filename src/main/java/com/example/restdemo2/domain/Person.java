package com.example.restdemo2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title must not be null.")
    private String name;
    private Integer age;
    private Double salary;
    private Date dob;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();

    public Person(@NotBlank(message = "Title must not be null.") String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public enum Status{
        ACTIVE,
        INACTIVE;
    }
}
