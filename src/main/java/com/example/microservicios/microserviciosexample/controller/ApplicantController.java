package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.*;
import com.example.microservicios.microserviciosexample.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ApplicantController {
    @Autowired
    private IApplicanService apliServ;


    @GetMapping("/applicant/get")
    public List<Applicant> getApllicants(){
        return apliServ.getApplicants();

    }
    @GetMapping("/applicants/traer/{idApplicant}")
    public  Applicant findApp(@PathVariable Long idApplicant){

        return apliServ.findApp(idApplicant);
    }

    @PostMapping("/app/create")
    public ResponseEntity<?> createApp(@RequestBody Applicant app){
        try {

            apliServ.createApplicant(app);


            return new ResponseEntity<>(app, HttpStatus.CREATED);
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
    public ResponseEntity<Applicant> editAplicant(@PathVariable Long idApplicant, @RequestBody Applicant app) {
        try {
            Applicant updatedApplicant = apliServ.editApp(idApplicant, app);
            return ResponseEntity.ok(updatedApplicant);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
