package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.*;
import com.example.microservicios.microserviciosexample.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override

    public Applicant editApp(Long idApplicant, Applicant applicant) {
        try {
            Applicant aplicantFind = this.findApp(idApplicant);


            aplicantFind.setFirstName(applicant.getFirstName());
            aplicantFind.setSecondName(applicant.getSecondName());
            aplicantFind.setLastName(applicant.getLastName());
            aplicantFind.setLastName2(aplicantFind.getLastName2());
            aplicantFind.setSexo(applicant.getSexo());
            aplicantFind.setState(applicant.getState());
            aplicantFind.setParish(applicant.getParish());
            aplicantFind.setNationality(applicant.getNationality());
            aplicantFind.setMunicipality(applicant.getMunicipality());
            aplicantFind.setEmail(applicant.getEmail());
            aplicantFind.setDniType(applicant.getDniType());
            aplicantFind.setCountry(applicant.getCountry());
            aplicantFind.setCedula(applicant.getCedula());
            aplicantFind.setBirthDate(applicant.getBirthDate());
            aplicantFind.setBirthDate(applicant.getBirthDate());
            aplicantFind.setCellphone1(applicant.getCellphone1());
            aplicantFind.setCellphone2(applicant.getCellphone2());
            appRepo.save(aplicantFind);

            return aplicantFind;
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

    @Override
    public void deleteApplicant(Long idApplicant) {
      appRepo.deleteById(idApplicant);
    }
    @Override
    public Applicant saveApplicant(Applicant applicant) {
        return appRepo.save(applicant);
    }
}
