package com.example.microservicios.microserviciosexample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long idPosition;
    private String positionApp;
    private Double salary;
    private String disponibility;
    @OneToOne
    @JsonIgnore
    private Applicant applicant;
}
