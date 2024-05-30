package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Jobs;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.repository.IJobsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JobService implements IJobsServicce{
    @Autowired
    private IJobsRepository jobRepo;
    @Override
    public List<Jobs> getJobs() {
       List<Jobs> jobsList= jobRepo.findAll();
       return jobsList;
    }

    @Override
    public void SaveJobs(Jobs jobs) {
     jobRepo.save(jobs);
    }

    @Override
    public void deleteJobs(Long idJob) {
      jobRepo.deleteById(idJob);
    }

    @Override
    public Jobs findJob(Long idJob) {
     Jobs jobs= jobRepo.findById(idJob).orElseThrow(null);
     return  jobs;

    }

    @Override
    public void editJob(Jobs job) {
    jobRepo.save(job);
    }
}
