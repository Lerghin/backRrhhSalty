package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoursesRepository extends JpaRepository<Courses, Long> {
}
