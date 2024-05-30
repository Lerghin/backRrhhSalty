package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Studies;
import com.example.microservicios.microserviciosexample.model.User;

import java.util.List;

public interface IStudiesService {
    public List<Studies> getStudies();
    public void SaveStudy(Studies studies);

    public void deleteStudy(Long idStudy);

    public  User findStudy(Long idStudy);

    public void editStudy( Studies study);

}
