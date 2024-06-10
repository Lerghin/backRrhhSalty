package com.example.microservicios.microserviciosexample.auth;

import com.example.microservicios.microserviciosexample.jwt.JwtService;
import com.example.microservicios.microserviciosexample.model.Nationality;
import com.example.microservicios.microserviciosexample.model.Role;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new GlobalExceptionHandler.MissingDataException("Username or password is missing");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new GlobalExceptionHandler.InvalidCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new GlobalExceptionHandler.InvalidCredentialsException("User not found"));
        String token = jwtService.getToken(userDetails);

       String role= userDetails.getAuthorities().stream().findFirst().orElseThrow().getAuthority();




        return AuthResponse.builder()
                .token(token)
                .role(role)
                .build();
    }


    public AuthResponse registerUser(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }
        if (userRepository.existsByCedula(request.getCedula())) {
            throw new IllegalArgumentException("La cédula ya está registrada");
        }
        Nationality nationality = Nationality.VENEZOLANO; // Valor predeterminado
        if (request.getNationality() != null) {
            try {
                nationality = Nationality.valueOf(request.getNationality().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Valor de nacionalidad no válido: " + request.getNationality());
            }
        }
        User user= User.builder()
                .username(request.username)
                .password(passwordEncoder.encode(request.password))
                .firstName(request.firstName)
                .lastName(request.lastName)
                .cedula(request.cedula)
                .role(Role.USER)
                .nationality(nationality) // Asignar la nacionalidad proporcionada
                .build();
        userRepository.save(user);
        String role= user.getRole().toString();
        String id= user.getId().toString();
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .role(role)
                .id(id)
                .build();
    }

    public AuthResponse registerAdmin(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }
        if (userRepository.existsByCedula(request.getCedula())) {
            throw new IllegalArgumentException("La cédula ya está registrada");
        }
        Nationality nationality = Nationality.VENEZOLANO; // Valor predeterminado
        if (request.getNationality() != null) {
            try {
                nationality = Nationality.valueOf(request.getNationality().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Valor de nacionalidad no válido: " + request.getNationality());
            }
        }
        User user= User.builder()
                .username(request.username)
                .password(passwordEncoder.encode(request.password))
                .firstName(request.firstName)
                .lastName(request.lastName)
                .cedula(request.cedula)
                .role(Role.ADMIN)
                .nationality(nationality) // Asignar la nacionalidad proporcionada
                .build();
        userRepository.save(user);
        String role= user.getRole().toString();
        String id= user.getId().toString();
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .role(role)
                .id(id)
                .build();
    }


}
