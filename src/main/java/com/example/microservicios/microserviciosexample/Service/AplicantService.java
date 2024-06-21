package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.*;
import com.example.microservicios.microserviciosexample.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        try {


            // Guardar Applicant
            Applicant savedApplicant = appRepo.save(applicant);

            User user = userRepo.findByCedula(applicant.getCedula());
            user.setApplicant(savedApplicant);


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
            if (applicant.getUser() != null) {
                UserApplicantDTO userApplicantDTO = new UserApplicantDTO();
                userApplicantDTO.setIdApplicant(applicant.getIdApplicant());
                userApplicantDTO.setUsername(applicant.getUser().getUsername());
                userApplicantDTO.setCedula(applicant.getCedula());
                userApplicantDTO.setId(applicant.getUser().getId());
                User user1 = userRepo.findById(userApplicantDTO.getId()).orElseThrow(null);
                savedApplicant.setUser(user1);
                appRepo.save(savedApplicant);

            }


            return savedApplicant;
        } catch(Exception e) {
            throw new RuntimeException("Error al crear el aplicante", e);
        }
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
    public Applicant updateApplicant(Long id, ApplicantDto applicantDto) {
        return null;

    }



    @Transactional
    public Applicant editApp(Long idApplicant, Applicant updatedApplicant) {
        try {
            // Obtener el aplicante existente por ID
            Applicant existingApplicant = appRepo.findById(idApplicant)
                    .orElseThrow(() -> new EntityNotFoundException("Applicant with id " + idApplicant + " not found"));

            // Actualizar campos del aplicante existente
            updateApplicantFields(existingApplicant, updatedApplicant);

            // Actualizar relación con User
            if (updatedApplicant.getUser() != null) {
                User user = userRepo.findById(updatedApplicant.getUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User with id " + updatedApplicant.getUser().getId() + " not found"));
                existingApplicant.setUser(user);
            } else {
                existingApplicant.setUser(null); // Si se establece a null en la actualización
            }

            // Guardar el aplicante actualizado
            Applicant savedApplicant = appRepo.save(existingApplicant);

            // Actualizar estudios
            updateStudies(savedApplicant, updatedApplicant.getStudiesList());

            // Actualizar trabajos
            updateJobs(savedApplicant, updatedApplicant.getJobsList());

            // Actualizar cursos
            updateCourses(savedApplicant, updatedApplicant.getCoursesList());

            return savedApplicant;

        } catch (Exception e) {
            throw new RuntimeException("Error al editar el aplicante", e);
        }
    }

    private void updateApplicantFields(Applicant existingApplicant, Applicant updatedApplicant) {
        existingApplicant.setFirstName(updatedApplicant.getFirstName());
        existingApplicant.setSecondName(updatedApplicant.getSecondName());
        existingApplicant.setLastName(updatedApplicant.getLastName());
        existingApplicant.setLastName2(updatedApplicant.getLastName2());
        existingApplicant.setNationality(updatedApplicant.getNationality());
        existingApplicant.setDniType(updatedApplicant.getDniType());
        existingApplicant.setCedula(updatedApplicant.getCedula());
        existingApplicant.setSexo(updatedApplicant.getSexo());
        existingApplicant.setBirthDate(updatedApplicant.getBirthDate());
        existingApplicant.setEmail(updatedApplicant.getEmail());
        existingApplicant.setCellphone1(updatedApplicant.getCellphone1());
        existingApplicant.setCellphone2(updatedApplicant.getCellphone2());
        existingApplicant.setCountry(updatedApplicant.getCountry());
        existingApplicant.setNombreDeProfesion(updatedApplicant.getNombreDeProfesion());
        existingApplicant.setAddress(updatedApplicant.getAddress());
        existingApplicant.setState(updatedApplicant.getState());
        existingApplicant.setParish(updatedApplicant.getParish());
        existingApplicant.setMunicipality(updatedApplicant.getMunicipality());
        existingApplicant.setSalary(updatedApplicant.getSalary());
        existingApplicant.setDisponibility(updatedApplicant.getDisponibility());
    }

    private void updateStudies(Applicant applicant, List<Studies> updatedStudies) {
        // Eliminar estudios existentes que no están en la lista actualizada
        List<Studies> existingStudies = studRepo.findByApplicant(applicant);
        existingStudies.stream()
                .filter(study -> !updatedStudies.contains(study))
                .forEach(studRepo::delete);

        // Actualizar estudios y asignar al aplicante
        for (Studies studies : updatedStudies) {
            studies.setApplicant(applicant);
        }
        studRepo.saveAll(updatedStudies);
    }

    private void updateJobs(Applicant applicant, List<Jobs> updatedJobs) {
        // Eliminar trabajos existentes que no están en la lista actualizada
        List<Jobs> existingJobs = jobRepo.findByApplicant(applicant);
        existingJobs.stream()
                .filter(job -> !updatedJobs.contains(job))
                .forEach(jobRepo::delete);

        // Actualizar trabajos y asignar al aplicante
        for (Jobs jobs : updatedJobs) {
            jobs.setApplicant(applicant);
        }
        jobRepo.saveAll(updatedJobs);
    }

    private void updateCourses(Applicant applicant, List<Courses> updatedCourses) {
        // Eliminar cursos existentes que no están en la lista actualizada
        List<Courses> existingCourses = cuRepo.findByApplicant(applicant);
        existingCourses.stream()
                .filter(course -> !updatedCourses.contains(course))
                .forEach(cuRepo::delete);

        // Actualizar cursos y asignar al aplicante
        for (Courses courses : updatedCourses) {
            courses.setApplicant(applicant);
        }
        cuRepo.saveAll(updatedCourses);
    }

    @Override
    public List<Vacante> getVacanByApp(Long idApplicant) {
        Applicant applicant= this.findApp(idApplicant);
        List<Vacante> vacanteList= applicant.getVacantes();
        return vacanteList;
    }
/*
    @Transactional
    public Applicant editApp(Long idApplicant, ApplicantDto updatedApplicantDTO) {
        try {
            // Obtener el aplicante existente por ID
            Applicant existingApplicant = appRepo.findById(idApplicant)
                    .orElseThrow(() -> new EntityNotFoundException("Applicant with id " + idApplicant + " not found"));

            // Actualizar campos del aplicante existente con datos del DTO
            existingApplicant.setFirstName(updatedApplicantDTO.getFirstName());
            existingApplicant.setSecondName(updatedApplicantDTO.getSecondName());
            existingApplicant.setLastName(updatedApplicantDTO.getLastName());
            existingApplicant.setLastName2(updatedApplicantDTO.getLastName2());
            existingApplicant.setNationality(updatedApplicantDTO.getNationality());
            existingApplicant.setDniType(updatedApplicantDTO.getDniType());
            existingApplicant.setCedula(updatedApplicantDTO.getCedula());
            existingApplicant.setSexo(updatedApplicantDTO.getSexo());
            existingApplicant.setBirthDate(updatedApplicantDTO.getBirthDate());
            existingApplicant.setEmail(updatedApplicantDTO.getEmail());
            existingApplicant.setCellphone1(updatedApplicantDTO.getCellphone1());
            existingApplicant.setCellphone2(updatedApplicantDTO.getCellphone2());
            existingApplicant.setCountry(updatedApplicantDTO.getCountry());
            existingApplicant.setNombreDeProfesion(updatedApplicantDTO.getNombreDeProfesion());
            existingApplicant.setAddress(updatedApplicantDTO.getAddress());
            existingApplicant.setState(updatedApplicantDTO.getState());
            existingApplicant.setParish(updatedApplicantDTO.getParish());
            existingApplicant.setMunicipality(updatedApplicantDTO.getMunicipality());
            existingApplicant.setSalary(updatedApplicantDTO.getSalary());
            existingApplicant.setDisponibility(updatedApplicantDTO.getDisponibility());

            // Actualizar estudios (si se proporciona en el DTO)
            if (updatedApplicantDTO.getStudiesList() != null && !updatedApplicantDTO.getStudiesList().isEmpty()) {
                List<Studies> updatedStudies = new ArrayList<>();
                for (StudyDTO studiesDTO : updatedApplicantDTO.getStudiesList()) {
                    Studies studies = new Studies();
                    studies.setApplicant(existingApplicant);

                    studies.setDegree(studiesDTO.getDegree());
                    studies.setInstitutionName(studiesDTO.getInstitutionName());
                    studies.setDescription(studiesDTO.getDescription());
                    studies.setStartDate(studiesDTO.getStartDate());
                    studies.setEndDate(studiesDTO.getEndDate());
                    updatedStudies.add(studies);
                }
                existingApplicant.setStudiesList(updatedStudies);
            } else {
                existingApplicant.getStudiesList().clear(); // Limpiar estudios si no se proporcionan en el DTO
            }

            // Actualizar trabajos (si se proporciona en el DTO)
            if (updatedApplicantDTO.getJobsList() != null && !updatedApplicantDTO.getJobsList().isEmpty()) {
                List<Jobs> updatedJobs = new ArrayList<>();
                for (JobDTO jobDTO : updatedApplicantDTO.getJobsList()) {
                    Jobs newJob = new Jobs();
                    newJob.setApplicant(existingApplicant);
                    // Establecer otros campos de Jobs desde el DTO
                    newJob.setJobName(jobDTO.getJobName());
                    newJob.setAddress(jobDTO.getAddress());
                    newJob.setPosition(jobDTO.getPosition());
                    newJob.setPhone(jobDTO.getPhone());
                    newJob.setDescription(jobDTO.getDescription());
                    newJob.setStartDate(jobDTO.getStartDate());
                    newJob.setEndDate(jobDTO.getEndDate());
                    newJob.setPeopleSubordinated(jobDTO.getPeopleSubordinated());
                    newJob.setPositionBoss(jobDTO.getPositionBoss());
                    newJob.setReason(jobDTO.getReason());
                    updatedJobs.add(newJob);
                }
                existingApplicant.setJobsList(updatedJobs);
            } else {
                existingApplicant.getJobsList().clear(); // Limpiar trabajos si no se proporcionan en el DTO
            }

            // Actualizar cursos (si se proporciona en el DTO)
            if (updatedApplicantDTO.getCoursesList() != null && !updatedApplicantDTO.getCoursesList().isEmpty()) {
                List<Courses> updatedCourses = new ArrayList<>();
                for (CourseDTO courseDTO : updatedApplicantDTO.getCoursesList()) {
                    Courses course = new Courses();
                    course.setApplicant(existingApplicant);
                    // Establecer otros campos de Courses desde el DTO
                    course.setNameInstitution(courseDTO.getNameInstitution());
                    course.setDate(courseDTO.getDate());
                    course.setNameCourse(courseDTO.getNameCourse());
                    updatedCourses.add(course);
                }
                existingApplicant.setCoursesList(updatedCourses);
            } else {
                existingApplicant.getCoursesList().clear(); // Limpiar cursos si no se proporcionan en el DTO
            }

            // Actualizar relación con User (si existe en el DTO)
            if (updatedApplicantDTO.getUser() != null) {
                User user = userRepo.findById(updatedApplicantDTO.getUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User with id " + updatedApplicantDTO.getUser().getId() + " not found"));
                existingApplicant.setUser(user);
            } else {
                existingApplicant.setUser(null); // Si se establece a null en el DTO
            }

            // Guardar el aplicante actualizado
            Applicant savedApplicant = appRepo.save(existingApplicant);

            return savedApplicant;

        } catch (Exception e) {
            throw new RuntimeException("Error al editar el aplicante", e);
        }
    }


*/

}
