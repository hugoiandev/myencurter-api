package com.myencurter.dto.url;

import jakarta.validation.constraints.Pattern;

public record UrlRequestDTO(

        @Pattern(regexp = "^((ftp|http|https)://[a-z0-9-]+\\.[a-z0-9-.]+(/[a-z0-9-._~:/&?#+!@*()=]*)?)$", message = "Informe uma url válida.")
        String url
) {}
