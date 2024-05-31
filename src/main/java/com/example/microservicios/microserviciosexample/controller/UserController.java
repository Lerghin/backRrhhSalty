package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.UserService;
import com.example.microservicios.microserviciosexample.model.User;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

   @Autowired
    private UserService userService;

   @GetMapping("user/get")
    public List<User> getUsers(){
       return  userService.getUsers();
   }
   @GetMapping("/user/{id}")
    public User findUSer(@PathVariable Long id){
       return userService.findUSer(id);
   }
    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
       userService.deleteUser(id);
        return " was deleted";


    }

    @PutMapping("/user/edit")
    public String editUser(@RequestBody User user){
       userService.editUser(user);
        return "was edited succesfully";
    }


}
