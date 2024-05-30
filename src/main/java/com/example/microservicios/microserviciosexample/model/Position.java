package com.example.microservicios.microserviciosexample.model;

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
    private String PositionApp;
    private Double salary;
    private String Disponibility;
    @OneToOne
    private Applicant applicant;
}
