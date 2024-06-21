package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.UserService;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.model.UserApplicantDTO;
import com.example.microservicios.microserviciosexample.model.Vacante;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "https://front-salty-rrhh.vercel.app")
@RestController
public class UserController {

   @Autowired
    private UserService userService;

    @GetMapping("/users/traer")
    public List<User> getUser(){
        return userService.getUsers();

    }
   @GetMapping("/users/{id}")
    public UserApplicantDTO findUSer(@PathVariable Long id){
       return userService.findUSer(id);
   }
    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
       userService.deleteUser(id);
        return " was deleted";


    }

    @PutMapping("/users/edit")
    public String editUser(@RequestBody User user){
       userService.editUser(user);
        return "was edited succesfully";
    }




}
