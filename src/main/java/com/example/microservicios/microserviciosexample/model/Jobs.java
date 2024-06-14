package com.example.microservicios.microserviciosexample.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(columnDefinition = "TEXT")
    private String address;

    private String phone;

    private String position;
    private Integer peopleSubordinated;
    @Column(columnDefinition = "TEXT")
    private String positionBoss;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(columnDefinition = "TEXT")
    private String reason;
    @ManyToOne
    @JsonIgnore
    private Applicant applicant;






}
