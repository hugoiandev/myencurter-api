package com.myencurter.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @Email(message = "Informe um email válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String password
) {}
