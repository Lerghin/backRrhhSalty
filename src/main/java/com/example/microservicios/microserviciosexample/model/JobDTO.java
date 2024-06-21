package com.example.microservicios.microserviciosexample.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobDTO {
    private Long idJob;


    private  String jobName;

    private String address;

    private String phone;

    private String position;
    private Integer peopleSubordinated;

    private String positionBoss;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;
}
