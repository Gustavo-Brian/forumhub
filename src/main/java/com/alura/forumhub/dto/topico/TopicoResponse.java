package com.alura.forumhub.dto.topico;

import java.time.LocalDateTime;

public record TopicoResponse(
    Long id, String titulo, String mensagem, LocalDateTime dataCriacao,
    Long cursoId, String cursoNome, Long autorId, String autorNome
){}
