package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Jobs;

import java.util.List;

public interface IJobsServicce {
    public List<Jobs> getJobs();
    public void SaveJobs(Jobs jobs);

    public void deleteJobs(Long idJob);

    public  Jobs findJob(Long idJob);

    public Jobs editJob(Long idJob, Jobs job);

}
