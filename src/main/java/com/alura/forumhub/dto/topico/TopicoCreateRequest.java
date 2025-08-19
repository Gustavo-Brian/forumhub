package com.alura.forumhub.dto.topico;

import jakarta.validation.constraints.*;

public record TopicoCreateRequest(
    @NotBlank @Size(max=100) String titulo,
    @NotBlank @Size(max=500) String mensagem,
    @NotBlank String nomeCurso
){}
