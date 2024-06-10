package com.example.microservicios.microserviciosexample.Service;

import com.example.microservicios.microserviciosexample.model.Position;

import java.util.List;

public interface IPositionService {
    public List<Position> getPositions();
    public void SavePosition(Position position);

    public void deletePosition(Long idPosition);

    public  Position findPosition(Long idPosition);

    public Position editPosition(Position position);

}
