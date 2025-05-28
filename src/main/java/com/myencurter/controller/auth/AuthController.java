package com.myencurter.controller.auth;

import com.myencurter.dto.auth.LoginRequestDTO;
import com.myencurter.dto.auth.RegisterRequestDTO;
import com.myencurter.dto.auth.AuthResponseDTO;
import com.myencurter.model.User;
import com.myencurter.security.TokenService;
import com.myencurter.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO body) {

        User user = authService.getUser(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new AuthResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO body) {

        Optional<User> user = authService.getUser(body.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            authService.saveUser(newUser);

            String token = tokenService.generateToken(newUser);

            return ResponseEntity.ok(new AuthResponseDTO(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
