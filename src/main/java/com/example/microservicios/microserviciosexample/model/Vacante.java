package com.example.microservicios.microserviciosexample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Vacantes")
public class Vacante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private LocalDate fecha;
    private String nombreVacante;
    private Integer cantidadDisponible;
    private String status;

    @ManyToMany
    @JoinTable(
            name = "vacante_applicant",  // Nombre de la tabla de unión
            joinColumns = @JoinColumn(name = "vacante_id"),  // Clave foránea hacia Vacante
            inverseJoinColumns = @JoinColumn(name = "applicant_id")  // Clave foránea hacia Applicant
    )
    @JsonIgnore
    private List<Applicant> aplicantList;




}
