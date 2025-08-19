package com.alura.forumhub.dto.auth;

import jakarta.validation.constraints.*;

public record LoginRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min=6) String senha
){}
