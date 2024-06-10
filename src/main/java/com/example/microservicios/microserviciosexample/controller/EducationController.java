package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.ICourseService;
import com.example.microservicios.microserviciosexample.Service.IStudiesService;
import com.example.microservicios.microserviciosexample.model.Courses;
import com.example.microservicios.microserviciosexample.model.Studies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class EducationController {
    @Autowired
    private IStudiesService stuServ;


    @GetMapping("/study/get")
    public List<Studies> getStudios(){
        return stuServ.getStudies();

    }
    @GetMapping("/study/get/{idStudy}")
    public  Studies findStudy(@PathVariable Long idStudy){

        return  stuServ.findStudy(idStudy);
    }

    @PostMapping("/study/create")
    public ResponseEntity<?> createApp(@RequestBody Studies study ) {
        try {

           stuServ.SaveStudy(study);


            return new ResponseEntity<>(study, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el Estudio: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/study/delete/{idStudy}")
    public String deleteStudy(@PathVariable Long idStudy){
        stuServ.deleteStudy(idStudy);

        return "fue borrado el Estudio ";


    }

    @PutMapping("/study/edit/{idStudy}")
    public ResponseEntity<Studies> editStudy(@PathVariable Long idStudy, @RequestBody Studies studies) {
        try {
            Studies studies1 = stuServ.editStudy(idStudy, studies);
            return ResponseEntity.ok(studies1);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
