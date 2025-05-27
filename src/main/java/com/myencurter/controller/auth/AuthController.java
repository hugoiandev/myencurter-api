package com.myencurter.controller.auth;

import com.myencurter.dto.auth.LoginRequestDTO;
import com.myencurter.dto.auth.RegisterRequestDTO;
import com.myencurter.dto.auth.AuthResponseDTO;
import com.myencurter.model.User;
import com.myencurter.repository.UserRepository;
import com.myencurter.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new AuthResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO body, BindingResult bindingResult) {
        Optional<User> user = repository.findByEmail(body.email());

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors().stream().map(err -> err.getDefaultMessage()));
        }

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            repository.save(newUser);

            String token = tokenService.generateToken(newUser);

            return ResponseEntity.ok(new AuthResponseDTO(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
