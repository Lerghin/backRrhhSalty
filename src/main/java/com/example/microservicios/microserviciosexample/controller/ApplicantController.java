package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.*;
import com.example.microservicios.microserviciosexample.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ApplicantController {
    @Autowired
    private IApplicanService apliServ;

    @Autowired
    private IVacannteService vacaServ;

    @GetMapping("/applicant/get")
    public List<Applicant> getApllicants(){
        return apliServ.getApplicants();

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

    @PutMapping("/app/editar")
    public ResponseEntity<Applicant> editAplicant( @RequestBody Applicant app) {
        try {
            Applicant updatedApplicant = apliServ.editApp( app);
            return ResponseEntity.ok(updatedApplicant);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/{idApplicant}/apply/{id}")
    public ResponseEntity<String> applyToVacante(@PathVariable Long idApplicant, @PathVariable Long id) {
        try {
            Applicant applicant = apliServ.findApp(idApplicant);
            Vacante vacante = vacaServ.findVacante(id);

            if (applicant == null || vacante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Applicant or Vacante not found");
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
