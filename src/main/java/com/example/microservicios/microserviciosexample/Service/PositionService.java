package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Position;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.repository.IPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PositionService implements IPositionService{
    @Autowired
    private IPositionRepository poRepo;
    @Override
    public List<Position> getPositions() {
        List<Position> positionList= poRepo.findAll();
        return  positionList;
    }

    @Override
    public void SavePosition(Position position) {
     poRepo.save(position);
    }

    @Override
    public void deletePosition(Long idPosition) {
      poRepo.deleteById(idPosition);
    }

    @Override
    public Position findPosition(Long idPosition) {
      Position position= poRepo.findById(idPosition).orElseThrow(null);
      return  position;
    }

    @Override
    public void editPosition(Position position) {
    poRepo.save(position);
    }
}
