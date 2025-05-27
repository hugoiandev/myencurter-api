package com.myencurter.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @Email(message = "Informe um email válido.")
        String email,

        @Length(min = 8, message = "A senha deve ser maior ou igual a 8 caracteres.")
        String password
) {}
