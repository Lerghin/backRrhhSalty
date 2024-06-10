package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.IApplicanService;
import com.example.microservicios.microserviciosexample.Service.ICourseService;
import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CoursesController {

    @Autowired
    private ICourseService curServ;


    @GetMapping("/courses/get")
    public List<Courses> getCourses(){
        return curServ.getCourses();

    }
    @GetMapping("/courses/get/{idCourse}")
    public  Courses findCourses(@PathVariable Long idCourse){

        return  curServ.findCourse(idCourse);
    }

    @PostMapping("/course/create")
    public ResponseEntity<?> createApp(@RequestBody Courses course ) {
        try {

            curServ.SaveCourse(course);


            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el Curso : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/course/delete/{idCourse}")
    public String deleteCour(@PathVariable Long idCourse){
        curServ.deleteCourse(idCourse);
        return "fue borrado el Curso ";


    }

    @PutMapping("/course/edit/{idCourse}")
    public ResponseEntity<Courses> editCourse(@PathVariable Long idCourse, @RequestBody Courses courses) {
        try {
           Courses curso = curServ.editCourse(idCourse, courses);
            return ResponseEntity.ok(curso);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
