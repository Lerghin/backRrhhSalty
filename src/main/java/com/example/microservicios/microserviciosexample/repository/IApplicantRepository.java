package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.Courses;
import com.example.microservicios.microserviciosexample.model.Jobs;
import com.example.microservicios.microserviciosexample.model.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplicantRepository extends JpaRepository<Applicant, Long> {

    boolean existsByCedula(Integer cedula);

    boolean existsByEmail(String email);


}
