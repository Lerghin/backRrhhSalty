package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.repository.IApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AplicantService implements  IApplicanService{
    @Autowired
    private IApplicantRepository appRepo;
    @Override
    public List<Applicant> getApplicants() {

        List<Applicant> applicantList= appRepo.findAll();
        return  applicantList;
    }

    @Override
    public void SaveAplicant(Applicant applicant) {
        appRepo.save(applicant);
    }

    @Override
    public void deleteApplicant(Long idApplicant) {
      appRepo.deleteById(idApplicant);
    }

    @Override
    public Applicant findApp(Long idApplicant) {
        Applicant applicant= appRepo.findById(idApplicant).orElseThrow(null);
        return  applicant;


    }

    @Override
    public void editApp(Applicant applicant) {
       appRepo.save(applicant);
    }
}
