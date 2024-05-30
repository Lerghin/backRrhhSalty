package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Courses;
import com.example.microservicios.microserviciosexample.model.User;

import java.util.List;

public interface ICourseService {
    public List<Courses> getCourses();
    public void SaveCourse(Courses course);

    public void deleteCourse(Long idCourse);

    public  Courses findCourse(Long idCourse);

    public void editCourse( Courses course);

}
