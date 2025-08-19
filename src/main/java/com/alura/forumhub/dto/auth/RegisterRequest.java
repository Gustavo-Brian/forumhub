package com.alura.forumhub.dto.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank @Size(max=100) String nome,
    @NotBlank @Email String email,
    @NotBlank @Size(min=6) String senha
){}
