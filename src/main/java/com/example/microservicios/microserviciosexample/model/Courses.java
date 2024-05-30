package com.example.microservicios.microserviciosexample.model;

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
    private String nameCourse;
    private LocalDate date;
    private String nameInstitution;

    @ManyToOne
    private Applicant applicant;
}
