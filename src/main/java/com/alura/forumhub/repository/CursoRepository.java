package com.alura.forumhub.repository;

import com.alura.forumhub.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNomeIgnoreCase(String nome);
}
