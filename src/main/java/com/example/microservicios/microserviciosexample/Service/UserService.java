package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.model.UserApplicantDTO;
import com.example.microservicios.microserviciosexample.repository.IApplicantRepository;
import com.example.microservicios.microserviciosexample.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements  IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IApplicantRepository appRepo;
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

       User user = userRepository.findById(id).orElseThrow(null);
      Applicant app= appRepo.findById(user.getApplicant().getIdApplicant()).orElseThrow(null);
        app.setUser(null);
      user.setApplicant(null);

        appRepo.save(app);
        userRepository.save(user);

            userRepository.delete(user);
            appRepo.delete(app);

    }


    @Override
    public UserApplicantDTO findUSer(Long id) {

        User user= userRepository.findById(id).orElseThrow(null);
        UserApplicantDTO userApplicantDTO= new UserApplicantDTO();
        userApplicantDTO.setId(user.getId());
        userApplicantDTO.setCedula(user.getCedula());
        userApplicantDTO.setIdApplicant(user.getApplicant().getIdApplicant());
        userApplicantDTO.setUsername(user.getUsername());
        return userApplicantDTO ;
    }

    @Override
    public void editUser(User user) {
        userRepository.save(user);
    }


}
