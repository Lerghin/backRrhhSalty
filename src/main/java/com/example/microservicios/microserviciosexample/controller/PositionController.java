package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.IPositionService;
import com.example.microservicios.microserviciosexample.model.Position;
import com.example.microservicios.microserviciosexample.model.Vacante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @GetMapping("/position/get")
    public List<Position> getVacantes(){
        return positionService.getPositions();

    }
    @GetMapping("/position/get/{idPosition}")
    public Position findPosition(@PathVariable Long idPosition){

        return positionService.findPosition(idPosition)  ;
    }

    @PostMapping("/position/create")
    public ResponseEntity<?> createPosition(@RequestBody Position position ) {
        try {

            positionService.SavePosition(position);


            return new ResponseEntity<>(position, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la posición: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/position/delete/{idPosition}")
    public String deletePos(@PathVariable Long idPosition){
        positionService.deletePosition(idPosition);

        return "fue borrada la posición ";


    }

    @PutMapping("/position/edit/{idPosition}")
    public ResponseEntity<Position> editPosition(@PathVariable Long idPosition, @RequestBody Position position) {
        try {
            Position position1= positionService.editPosition(position);
            return ResponseEntity.ok(position1);
        } catch (RuntimeException e) {
            // Manejar el error y retornar un ResponseEntity con un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
