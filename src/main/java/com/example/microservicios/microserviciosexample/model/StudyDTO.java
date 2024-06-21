package com.example.microservicios.microserviciosexample.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudyDTO {
    private Long  idStudy;
    private String institutionName;

    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

}
