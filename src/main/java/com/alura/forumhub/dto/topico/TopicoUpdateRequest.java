package com.alura.forumhub.dto.topico;

import jakarta.validation.constraints.*;

public record TopicoUpdateRequest(
    @Size(max=100) String titulo,
    @Size(max=500) String mensagem
){}
