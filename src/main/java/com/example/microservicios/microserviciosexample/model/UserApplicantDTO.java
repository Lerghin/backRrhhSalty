package com.example.microservicios.microserviciosexample.model;

import lombok.Data;

@Data
public class UserApplicantDTO {
    private Long id;
    private String username;

    private Integer cedula;

    private Long idApplicant;

}
