package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobsRepository extends JpaRepository<Jobs, Long> {
}
