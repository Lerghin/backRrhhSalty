package com.example.microservicios.microserviciosexample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data@Builder@Entity@NoArgsConstructor @AllArgsConstructor
@Table(name = "studies")
public class Studies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idStudy;
    private String institutionName;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    @ManyToOne
    @JsonIgnore
     private Applicant applicant;

}
