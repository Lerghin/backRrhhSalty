package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.*;
import com.example.microservicios.microserviciosexample.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "https://front-salty-rrhh.vercel.app")
@RestController
public class ApplicantController {
    @Autowired
    private IApplicanService apliServ;
    private IUserService userService;

    @Autowired
    private IVacannteService vacaServ;

    @GetMapping("/applicant/get")
    public List<Applicant> getApllicants(){
        return apliServ.getApplicants();

    }
    @GetMapping("/applicant/vacant/{idApplicant}")
    public List<Vacante> getVacanByApp(@PathVariable Long idApplicant){
        return apliServ.getVacanByApp(idApplicant);

    }
    @GetMapping("/applicants/traer/{idApplicant}")
    public  Applicant findApp(@PathVariable Long idApplicant){
        Applicant app= apliServ.findApp(idApplicant);
        return app;
    }
    @GetMapping("/applicants/studies/{idApplicant}")
    public ResponseEntity<?> findStudy(@PathVariable Long idApplicant) {
        List<Studies> studiesList = apliServ.getStudiesByApplicantId(idApplicant);
        if (studiesList == null || studiesList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El solicitante con ID " + idApplicant + " no tiene estudios registrados.");
        }
        return ResponseEntity.ok(studiesList);
    }

    @GetMapping("/applicants/jobs/{idApplicant}")
    public ResponseEntity<?> findJob(@PathVariable Long idApplicant) {
        List<Jobs> jobsList = apliServ.getJobsByApplicantId(idApplicant);
        if (jobsList == null || jobsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El solicitante con ID " + idApplicant + " no tiene trabajos registrados.");
        }
        return ResponseEntity.ok(jobsList);
    }
    @GetMapping("/applicants/users/{idApplicant}")
    public ResponseEntity<?> findUser(@PathVariable Long idApplicant) {
        User user= apliServ.getUser(idApplicant);
        if (user == null || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El solicitante con ID " + idApplicant + " no tiene trabajos registrados.");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/applicants/courses/{idApplicant}")
    public ResponseEntity<?> findCourses(@PathVariable Long idApplicant) {
        List<Courses> coursesList = apliServ.getCoursesByApplicantId(idApplicant);
        if (coursesList == null || coursesList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Devuelve una lista vacía si no hay cursos
        }

        return ResponseEntity.ok(coursesList);
    }


    @PostMapping("/app/create")
    public ResponseEntity<?> createApp(@RequestBody Applicant app) {
        try {

            Applicant createdApplicant = apliServ.createApplicant(app);
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            createdApplicant.setUser(currentUser);
            apliServ.saveApplicant(createdApplicant);
            return new ResponseEntity<>(createdApplicant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el Aplicante: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



        @DeleteMapping("/app/borrar/{idApplicant}")
    public String deleteApp(@PathVariable Long idApplicant){
        apliServ.deleteApplicant(idApplicant);
        return "fue borrado el Aplicant ";


    }

    @PutMapping("/app/editar/{idApplicant}")
    public ResponseEntity<Applicant> editApplicant(@PathVariable Long idApplicant, @RequestBody Applicant app) {
        Applicant updatedApplicant = apliServ.editApp(idApplicant, app);
        return ResponseEntity.ok(updatedApplicant);
    }
    @PostMapping("/{idApplicant}/apply/{id}")
    public ResponseEntity<String> applyToVacante(@PathVariable Long idApplicant, @PathVariable Long id) {
        try {
            Applicant applicant = apliServ.findApp(idApplicant);
            Vacante vacante = vacaServ.findVacante(id);

            if (applicant == null || vacante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Applicant or Vacante not found");
            }
            for(Vacante vacanteIndex: applicant.getVacantes()){
                if(vacanteIndex.getId()== vacante.getId()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya estas postulado a esta Vacante");
                }

            }

            applicant.getVacantes().add(vacante);
            vacante.getAplicantList().add(applicant);
            apliServ.saveApplicant(applicant);
            vacaServ.SaveVacante(vacante);

            return ResponseEntity.ok("Applicant applied to Vacante successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to apply to Vacante: " + e.getMessage());
        }
    }

}
