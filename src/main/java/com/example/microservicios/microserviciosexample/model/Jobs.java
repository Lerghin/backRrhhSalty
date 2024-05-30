package com.example.microservicios.microserviciosexample.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity@Data
@Builder@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs")
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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
    @ManyToOne
    private Applicant applicant;






}
