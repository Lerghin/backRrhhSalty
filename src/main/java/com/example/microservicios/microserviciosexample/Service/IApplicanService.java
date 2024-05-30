package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.Courses;
import com.example.microservicios.microserviciosexample.model.User;

import java.util.List;

public interface IApplicanService {

    public List<Applicant> getApplicants();
    public void SaveAplicant(Applicant applicant);

    public void deleteApplicant(Long idApplicant);

    public Applicant findApp(Long idApplicant);

    public void editApp( Applicant applicant);

}
