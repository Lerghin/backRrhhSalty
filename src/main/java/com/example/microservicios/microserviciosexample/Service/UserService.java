package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements  IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<User> getUsers() {

        return userRepository.findAll();

    }

    @Override
    public void SaveUser(User user) {
        userRepository.save(user);

    }

    @Transactional
    public void deleteUser(Long id) {
        // Busca al usuario por ID
        User user = this.findUSer(id);

        // Elimina el usuario y, debido a la configuración de cascade y orphanRemoval, también eliminará el Applicant asociado
        userRepository.delete(user);
    }

    @Override
    public User findUSer(Long id) {

        User user= userRepository.findById(id).orElseThrow(null);
        return user;
    }

    @Override
    public void editUser(User user) {
        userRepository.save(user);
    }


}
