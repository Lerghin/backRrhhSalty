package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Vacante;
import com.example.microservicios.microserviciosexample.repository.IVacantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacanteService implements  IVacannteService{

    @Autowired
    private IVacantesRepository vacaRepo;
    @Override
    public List<Vacante> getVacantes() {
       return vacaRepo.findAll();
    }

    @Override
    public void SaveVacante(Vacante vacante) {
      vacaRepo.save(vacante);
    }

    @Override
    public void deleteVacante(Long id) {
      vacaRepo.deleteById(id);
    }

    @Override
    public Vacante findVacante(Long id) {
        Vacante vacante= vacaRepo.findById(id).orElseThrow(null);
        return  vacante;
    }

    @Override
    public Vacante editVacante(Long id, Vacante vacante) {

       vacaRepo.save(vacante);
        return vacante;
    }
}
