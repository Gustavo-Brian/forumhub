package com.alura.forumhub.domain.topico;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Topico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String titulo;

    @NotBlank @Size(max = 500)
    private String mensagem;

    private LocalDateTime dataCriacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @PrePersist
    public void prePersist(){
        if (dataCriacao == null) dataCriacao = LocalDateTime.now();
    }
}
