package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Vacante;

import java.util.List;

public  interface IVacannteService {
    public List<Vacante> getVacantes();
    public void SaveVacante(Vacante vacante);

    public void deleteVacante(Long id);

    public  Vacante findVacante(Long id);

    public Vacante editVacante(Long id, Vacante vacante);


}
