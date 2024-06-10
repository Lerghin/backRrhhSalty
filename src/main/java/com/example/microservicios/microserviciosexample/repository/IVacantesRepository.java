package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVacantesRepository extends JpaRepository<Vacante, Long> {
}
