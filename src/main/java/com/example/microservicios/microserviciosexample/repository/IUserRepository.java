package com.example.microservicios.microserviciosexample.repository;

import com.example.microservicios.microserviciosexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
   

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByCedula(Integer cedula);


   User findByCedula(Integer cedula);
}
