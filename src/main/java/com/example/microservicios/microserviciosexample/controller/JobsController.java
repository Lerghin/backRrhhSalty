package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.IJobsServicce;
import com.example.microservicios.microserviciosexample.Service.IStudiesService;
import com.example.microservicios.microserviciosexample.model.Jobs;
import com.example.microservicios.microserviciosexample.model.Studies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "https://front-salty-rrhh.vercel.app")
@RestController
public class JobsController {
    @Autowired
    private IJobsServicce jobServ;


    @GetMapping("/jobs/get")
    public List<Jobs> getJobs(){
        return jobServ.getJobs();

    }
    @GetMapping("/jobs/get/{idJob}")
    public  Jobs findJob(@PathVariable Long idJob){

        return jobServ.findJob(idJob);
    }

    @PostMapping("/jobs/create")
    public ResponseEntity<?> createJobs(@RequestBody Jobs job ) {
        try {

           jobServ.SaveJobs(job);


            return new ResponseEntity<>(job, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el Trabajo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/jobs/delete/{idJob}")
    public String deleteStudy(@PathVariable Long idJob){
        jobServ.deleteJobs(idJob);

        return "fue borrado el Trabajo ";


    }

    @PutMapping("/jobs/edit/{idJob}")
    public ResponseEntity<Jobs> editJob(@PathVariable Long idJob, @RequestBody Jobs job) {
        try {
            Jobs job1 = jobServ.editJob(idJob, job);
            return ResponseEntity.ok(job1);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
