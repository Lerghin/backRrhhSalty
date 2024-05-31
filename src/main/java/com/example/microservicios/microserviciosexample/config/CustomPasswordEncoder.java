package com.example.microservicios.microserviciosexample.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    private final PasswordEncoder bcryptPasswordEncoder= new BCryptPasswordEncoder();
    @Override
    public String encode(CharSequence rawPassword) {
        return bcryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String password= rawPassword.toString();
        return tieneMayuscula(password) && caractEspeciales(password) && tieneLongitud(password) && bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    private boolean tieneMayuscula(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean caractEspeciales(String password) {
        return password.matches(".*[!@#$%^&*].*");
    }
    private boolean tieneLongitud(String password) {
        return password.length() >= 10;
    }
}
