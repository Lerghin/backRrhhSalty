package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.Courses;
import com.example.microservicios.microserviciosexample.model.Jobs;
import com.example.microservicios.microserviciosexample.model.Studies;

import java.util.List;

public interface IApplicanService {

    public List<Applicant> getApplicants();
    public Applicant createApplicant(Applicant applicant);

    public void deleteApplicant(Long idApplicant);

    public Applicant findApp(Long idApplicant);

    public Applicant editApp(Long idApplicant, Applicant applicant);

    public List<Studies> getStudiesByApplicantId(Long idApplicant);
    public List<Jobs> getJobsByApplicantId(Long idApplicant);
    public List<Courses> getCoursesByApplicantId(Long idApplicant);

}
