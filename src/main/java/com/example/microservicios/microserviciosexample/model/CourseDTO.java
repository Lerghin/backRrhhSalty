package com.example.microservicios.microserviciosexample.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseDTO {

    private Long idCourse;

    private String nameCourse;
    private LocalDate date;

    private String nameInstitution;


}
