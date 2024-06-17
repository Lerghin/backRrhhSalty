package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.model.UserApplicantDTO;

import java.util.List;

public interface IUserService {
    public List<User> getUsers();
    public void SaveUser(User user);

    public void deleteUser(Long id);

    public UserApplicantDTO findUSer(Long id);

    public void editUser( User user);



}
