package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.Studies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudiesRepository  extends JpaRepository<Studies, Long> {
    List<Studies> findByApplicant(Applicant applicant);
}
