package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Studies;
import com.example.microservicios.microserviciosexample.repository.IStudiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudiesService implements IStudiesService {
    @Autowired
    private IStudiesRepository studiesRepository;
    @Override
    public List<Studies> getStudies() {
        return studiesRepository.findAll();
    }

    @Override
    public void SaveStudy(Studies studies) {
      studiesRepository.save(studies);
    }

    @Override
    public void deleteStudy(Long idStudy) {
      studiesRepository.deleteById(idStudy);
    }

    @Override
    public Studies findStudy(Long idStudy) {
       Studies studies= studiesRepository.findById(idStudy).orElseThrow(null);
       return studies;
    }

    @Override
    public Studies editStudy(Long idStudy, Studies study) {
      studiesRepository.save(study);
        return study;
    }
}
