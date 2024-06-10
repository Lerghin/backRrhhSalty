package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.IJobsServicce;
import com.example.microservicios.microserviciosexample.Service.IVacannteService;
import com.example.microservicios.microserviciosexample.model.Jobs;
import com.example.microservicios.microserviciosexample.model.Vacante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class VacanteController {
    @Autowired
    private IVacannteService vacaServ;


    @GetMapping("/vacant/get")
    public List<Vacante> getVacantes(){
        return vacaServ.getVacantes();

    }
    @GetMapping("/vacant/get/{id}")
    public  Vacante findVac(@PathVariable Long id){

        return  vacaServ.findVacante(id);
    }

    @PostMapping("/vacant/create")
    public ResponseEntity<?> createVacant(@RequestBody Vacante vacante ) {
        try {

           vacaServ.SaveVacante(vacante);


            return new ResponseEntity<>(vacante, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la vacante: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/vacant/delete/{id}")
    public String deleteVacant(@PathVariable Long id){
       vacaServ.deleteVacante(id);

        return "fue borrada la vacante ";


    }

    @PutMapping("/vacant/edit/{id}")
    public ResponseEntity<Vacante> editVacant(@PathVariable Long id, @RequestBody Vacante vacante) {
        try {
            Vacante vacante1 = vacaServ.editVacante(id, vacante);
            return ResponseEntity.ok(vacante1);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
