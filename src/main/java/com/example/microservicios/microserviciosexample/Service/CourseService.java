package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Courses;
import com.example.microservicios.microserviciosexample.repository.ICoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService  {
    @Autowired
    private ICoursesRepository curRepo;
    @Override
    public List<Courses> getCourses() {
      List<Courses> coursesList= curRepo.findAll();
      return coursesList;
    }

    @Override
    public void SaveCourse(Courses course) {
        curRepo.save(course);


    }

    @Override
    public void deleteCourse(Long idCourse) {
       curRepo.deleteById(idCourse);
    }

    @Override
    public Courses findCourse(Long idCourse) {
        Courses course= curRepo.findById(idCourse).orElseThrow(null);
        return  course;
    }

    @Override
    public Courses editCourse(Long idCourse, Courses course) {
        Courses cur=this.findCourse(idCourse);
        cur.setIdCourse(idCourse);
        cur.setApplicant(course.getApplicant());
        cur.setNameCourse(course.getNameCourse());
        cur.setDate(course.getDate());
        cur.setNameInstitution(course.getNameInstitution());
        curRepo.save(course);

        return cur;
    }
}
