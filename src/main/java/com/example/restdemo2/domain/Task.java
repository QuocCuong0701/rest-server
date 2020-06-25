package com.example.restdemo2.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{title}")
    private String title;

    @NotBlank(message = "{description}")
    @Lob
    private String description;

    @Lob
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
}
