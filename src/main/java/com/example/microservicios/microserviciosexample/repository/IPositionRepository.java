package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Long> {
}
