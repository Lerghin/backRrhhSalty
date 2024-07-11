package com.example.microservicios.microserviciosexample.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO {
    private String[]toUser;
    private String subject;
    private String message;
}
