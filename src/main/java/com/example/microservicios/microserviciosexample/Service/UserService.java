package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.model.UserApplicantDTO;
import com.example.microservicios.microserviciosexample.repository.IApplicantRepository;
import com.example.microservicios.microserviciosexample.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
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

        // Encuentra el User por su ID, o lanza una excepción si no se encuentra.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        // Encuentra el Applicant asociado al User.
        Applicant applicant = user.getApplicant();

        // Si el User tiene un Applicant asociado:
        if (applicant != null) {
            // Desvincula el User del Applicant.
            applicant.setUser(null);
            user.setApplicant(null);

            // Guarda los cambios en ambas entidades para asegurar que la desvinculación se refleje en la base de datos.
            userRepository.save(user);
            appRepo.save(applicant);

            // Elimina las referencias en otras tablas antes de eliminar el Applicant.
            // Este paso es específico y depende de cómo estén modeladas otras entidades.
            // Ejemplo: vacanteApplicantRepository.deleteByApplicantId(applicant.getIdApplicant());

            // Ahora es seguro eliminar el Applicant.
            appRepo.delete(applicant);
        }

        // Elimina el User.
        userRepository.delete(user);
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
