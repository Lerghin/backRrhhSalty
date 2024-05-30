package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements  IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<User> getUsers() {
        List<User> userList= userRepository.findAll();
        return userList;

    }

    @Override
    public void SaveUser(User user) {
        userRepository.save(user);

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

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
