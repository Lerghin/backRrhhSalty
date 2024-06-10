package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Studies;

import java.util.List;

public interface IStudiesService {
    public List<Studies> getStudies();
    public void SaveStudy(Studies studies);

    public void deleteStudy(Long idStudy);

    public  Studies findStudy(Long idStudy);

    public Studies editStudy(Long idStudy, Studies study);

}
