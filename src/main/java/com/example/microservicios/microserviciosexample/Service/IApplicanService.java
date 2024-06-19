package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.*;

import java.util.List;

public interface IApplicanService {

    public List<Applicant> getApplicants();
    public Applicant createApplicant(Applicant applicant);

    public void deleteApplicant(Long idApplicant);

    public Applicant findApp(Long idApplicant);

    public Applicant editApp( Applicant applicant);
    public User getUser(Long idApplicant);

    public List<Studies> getStudiesByApplicantId(Long idApplicant);
    public List<Jobs> getJobsByApplicantId(Long idApplicant);
    public List<Courses> getCoursesByApplicantId(Long idApplicant);
    public void AppAplicacion(Long idApplicant, Long idVacante);
    public Applicant saveApplicant(Applicant applicant);

    public List<Vacante> getVacanByApp(Long idApplicant);

}
