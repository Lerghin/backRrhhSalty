package com.example.microservicios.microserviciosexample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Data@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCourse;
    @Column(columnDefinition = "TEXT")
    private String nameCourse;
    private LocalDate date;
    @Column(columnDefinition = "TEXT")
    private String nameInstitution;



    @ManyToOne
    @JsonIgnore
    private Applicant applicant;
}
