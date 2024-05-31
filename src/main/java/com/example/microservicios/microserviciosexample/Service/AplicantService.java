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
    private IPositionRepository poRepo;
    @Autowired
    private ICoursesRepository cuRepo;
    @Autowired
    private IJobsRepository jobRepo;
    @Override
    public List<Applicant> getApplicants() {

        List<Applicant> applicantList= appRepo.findAll();
        return  applicantList;
    }


    @Transactional
    public Applicant createApplicant(Applicant applicant) {
        // Guardar el solicitante principal
        Applicant savedApplicant = appRepo.save(applicant);

        // Guardar estudios y asociarlos con el solicitante
        if (savedApplicant.getStudiesList() != null) {
            for (Studies studies : savedApplicant.getStudiesList()) {
                studies.setApplicant(savedApplicant);
                studRepo.save(studies);
            }
        }

        // Guardar trabajos y asociarlos con el solicitante
        if (savedApplicant.getJobsList() != null) {
            for (Jobs jobs : savedApplicant.getJobsList()) {
                jobs.setApplicant(savedApplicant);
                jobRepo.save(jobs);
            }
        }

        // Guardar cursos y asociarlos con el solicitante
        if (savedApplicant.getCoursesList() != null) {
            for (Courses courses : savedApplicant.getCoursesList()) {
                courses.setApplicant(savedApplicant);
                cuRepo.save(courses);
            }
        }

        // Guardar la posición y asociarla con el solicitante
        if (savedApplicant.getPosition() != null) {
            Position position = savedApplicant.getPosition();
            position.setApplicant(savedApplicant);
            poRepo.save(position);
        }

        return savedApplicant;
    }


    @Override
    public Applicant findApp(Long idApplicant) {
        Applicant applicant= appRepo.findById(idApplicant).orElseThrow(null);
        return  applicant;


    }


    @Override

    public Applicant editApp(Long idApplicant, Applicant applicant) {
        try {
            Applicant aplicantFind = this.findApp(idApplicant);


            aplicantFind.setFirstname(applicant.getFirstname());
            aplicantFind.setSecondname(applicant.getSecondname());
            aplicantFind.setLastname(applicant.getLastname());
            aplicantFind.setLastname2(aplicantFind.getLastname2());
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
    public void deleteApplicant(Long idApplicant) {
        Optional<Applicant> optionalApplicant = appRepo.findById(idApplicant);
        if (optionalApplicant.isPresent()) {
            Applicant applicant = optionalApplicant.get();

            // Eliminar los registros relacionados en la tabla studies
            List<Studies> studiesList = applicant.getStudiesList();
            for (Studies studies : studiesList) {
                studRepo.delete(studies);
            }

            // Eliminar los registros relacionados en la tabla jobs
            List<Jobs> jobsList = applicant.getJobsList();
            for (Jobs jobs : jobsList) {
                jobRepo.delete(jobs);
            }

            // Eliminar los registros relacionados en la tabla courses
            List<Courses> coursesList = applicant.getCoursesList();
            for (Courses courses : coursesList) {
               cuRepo.delete(courses);
            }

            // Eliminar el registro relacionado en la tabla position
            Position position = applicant.getPosition();
            if (position != null) {
                poRepo.delete(position);
            }

            // Eliminar el registro en la tabla applicants
            appRepo.delete(applicant);
        } else {
            // Manejar el caso en el que no se encuentra el applicant con el ID dado
            throw new RuntimeException("no se pudo borrar");
        }
    }
}
