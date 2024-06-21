package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobsRepository extends JpaRepository<Jobs, Long> {
    List<Jobs> findByApplicant(Applicant applicant);
}
