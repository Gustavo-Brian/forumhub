package com.alura.forumhub.domain.curso;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "cursos", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Curso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 50)
    private String nome;
}
