package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IApplicantRepository extends JpaRepository<Applicant, Long> {

    boolean existsByCedula(Integer cedula);

    boolean existsByEmail(String email);
}
