package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.*;
import com.example.microservicios.microserviciosexample.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AplicantService implements  IApplicanService{
    @Autowired
    private IApplicantRepository appRepo;
    @Autowired
    private IStudiesRepository studRepo;

    @Autowired
    private ICoursesRepository cuRepo;
    @Autowired
    private IJobsRepository jobRepo;

    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private IVacantesRepository vacaRepo;

    @Override
    public List<Applicant> getApplicants() {

        List<Applicant> applicantList= appRepo.findAll();
        return  applicantList;
    }

    @Transactional
    public Applicant createApplicant(Applicant applicant) {
        // Guardar Applicant
        Applicant savedApplicant = appRepo.save(applicant);

        // Guardar estudios
        if (applicant.getStudiesList() != null) {
            for (Studies studies : applicant.getStudiesList()) {
                studies.setApplicant(savedApplicant);
                studRepo.save(studies);
            }
        }

        // Guardar trabajos
        if (applicant.getJobsList() != null) {
            for (Jobs jobs : applicant.getJobsList()) {
                jobs.setApplicant(savedApplicant);
                jobRepo.save(jobs);
            }
        }

        // Guardar cursos
        if (applicant.getCoursesList() != null) {
            for (Courses courses : applicant.getCoursesList()) {
                courses.setApplicant(savedApplicant);
                cuRepo.save(courses);
            }
        }
        if (applicant.getUser()!= null) {
           User user= userRepo.findById(applicant.getUser().getId()).orElseThrow(null);
           user.setApplicant(savedApplicant);
           userRepo.save(user);

        }




        return savedApplicant;
    }



    @Override
    public Applicant findApp(Long idApplicant) {
        Applicant applicant= appRepo.findById(idApplicant).orElseThrow(null);
       return applicant;
    }
    @Override
    public List<Studies> getStudiesByApplicantId(Long idApplicant) {
        Applicant applicant = this.findApp(idApplicant);
        return applicant.getStudiesList();
    }

    @Override
    public List<Jobs> getJobsByApplicantId(Long idApplicant) {
        Applicant applicant = this.findApp(idApplicant);
        return applicant.getJobsList();
    }

    @Override
    public List<Courses> getCoursesByApplicantId(Long idApplicant) {
        Applicant applicant = this.findApp(idApplicant);
        return applicant.getCoursesList();
    }

    public User getUser(Long idApplicant){
        Applicant applicant = this.findApp(idApplicant);
        return applicant.getUser();
    }


    @Transactional
    public Applicant editApp( Applicant applicant) {
        try {

            // Guardar el Applicant principal
            Applicant savedApplicant = appRepo.save(applicant);

            // Guardar estudios
            if (applicant.getStudiesList() != null && !applicant.getStudiesList().isEmpty()) {
                for (Studies studies : applicant.getStudiesList()) {
                    studies.setApplicant(savedApplicant);
                }
                studRepo.saveAll(applicant.getStudiesList());
            }

            // Guardar trabajos
            if (applicant.getJobsList() != null && !applicant.getJobsList().isEmpty()) {
                for (Jobs jobs : applicant.getJobsList()) {
                    jobs.setApplicant(savedApplicant);
                }
                jobRepo.saveAll(applicant.getJobsList());
            }

            // Guardar cursos
            if (applicant.getCoursesList() != null && !applicant.getCoursesList().isEmpty()) {
                for (Courses courses : applicant.getCoursesList()) {
                    courses.setApplicant(savedApplicant);
                }
                cuRepo.saveAll(applicant.getCoursesList());
            }

            return savedApplicant;

        } catch (Exception e) {
            throw new RuntimeException("Error al editar el aplicante", e);
        }
    }
    @Transactional
    public void AppAplicacion(Long idApplicant, Long idVacante) {
        // Buscar el applicant por su ID
        Applicant applicant = appRepo.findById(idApplicant)
                .orElseThrow(() -> new RuntimeException("Applicant no encontrado con ID: " + idApplicant));

        // Buscar la vacante por su ID
        Vacante vacante = vacaRepo.findById(idVacante)
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada con ID: " + idVacante));

        // Asociar el applicant a la vacante
        List<Vacante> vacanteList = applicant.getVacantes();
        vacanteList.add(vacante);

        // Guardar los cambios en el repositorio de applicants
        appRepo.save(applicant);
    }

    @Transactional
    public void deleteApplicant(Long idApplicant) {
        Applicant applicant= this.findApp(idApplicant);
        User user= userRepo.findById(applicant.getUser().getId()).orElseThrow(null);
        user.setApplicant(null);
        applicant.setUser(null);
        userRepo.save(user);
        appRepo.save(applicant);
        appRepo.deleteById(idApplicant);

    }
    @Override
    public Applicant saveApplicant(Applicant applicant) {
        return appRepo.save(applicant);
    }

    @Override
    public List<Vacante> getVacanByApp(Long idApplicant) {
        Applicant applicant= this.findApp(idApplicant);
        List<Vacante> vacanteList= applicant.getVacantes();
        return vacanteList;
    }
}
