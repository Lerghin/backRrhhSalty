package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Applicant;

import java.util.List;

public interface IApplicanService {

    public List<Applicant> getApplicants();
    public Applicant createApplicant(Applicant applicant);

    public void deleteApplicant(Long idApplicant);

    public Applicant findApp(Long idApplicant);

    public Applicant editApp(Long idApplicant, Applicant applicant);

}
