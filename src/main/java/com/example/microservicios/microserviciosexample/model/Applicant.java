package com.example.microservicios.microserviciosexample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplicant;
    @Column(nullable = false)
    private String firstName;
    private String secondName;

    @Column(nullable = false)
    private String lastName;
    private String lastName2;

    private Nationality  nationality;


    private String dniType;

    @Column(nullable = false, unique = true)
    private Integer cedula;

    private Sexo sexo;

    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String cellphone1;
    private String cellphone2;
    private String country;
    private String nombreDeProfesion;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT")
    private String state;


    private String parish;

    private String municipality;
    private Double salary;
    private String disponibility;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Studies> studiesList;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jobs> jobsList;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Courses> coursesList ;
    @OneToOne
    @JsonIgnore
    private User user;
    @ManyToMany(mappedBy = "aplicantList")
    private List<Vacante> vacantes;


    public int getEdad(){
        LocalDate nowDate= LocalDate.now();

        if(this.birthDate!= null){
            return Period.between(this.birthDate, nowDate).getYears();

        }else {
            return 0;

        }
    }


}
