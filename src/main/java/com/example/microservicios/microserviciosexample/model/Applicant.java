package com.example.microservicios.microserviciosexample.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


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
    private String firstname;
    private String secondname;

    @Column(nullable = false)
    private String lastname;
    private String lastname2;

    private Nationality  nationality;

    @Column(unique = true, nullable = false)
    private String dniType;

    @Column(nullable = false)
    private Integer cedula;

    private Sexo sexo;

    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cellphone1;
    private String cellphone2;
    private String country;
    private String address;

    private String state;

    private String parish;

    private String municipality;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Studies> studiesList;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jobs> jobsList;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Courses> coursesList ;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Position position;

    public int getEdad(){
        LocalDate nowDate= LocalDate.now();

        if(this.birthDate!= null){
            return Period.between(this.birthDate, nowDate).getYears();

        }else {
            return 0;

        }
    }


}
