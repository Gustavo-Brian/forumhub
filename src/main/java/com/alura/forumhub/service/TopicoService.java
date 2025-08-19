package com.alura.forumhub.service;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.dto.topico.*;
import com.alura.forumhub.exception.ForbiddenOperationException;
import com.alura.forumhub.repository.*;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    private final TopicoRepository topicos; private final CursoRepository cursos; private final UsuarioRepository usuarios;
    public TopicoService(TopicoRepository t, CursoRepository c, UsuarioRepository u){ this.topicos=t; this.cursos=c; this.usuarios=u; }

    public Page<TopicoResponse> list(String curso, Pageable pageable){
        Page<Topico> page = (curso==null || curso.isBlank()) ? topicos.findAll(pageable) : topicos.findByCurso_NomeIgnoreCase(curso, pageable);
        return page.map(this::toResponse);
    }

    public TopicoResponse get(Long id){
        return toResponse(topicos.findById(id).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Tópico não encontrado")));
    }

    public TopicoResponse create(TopicoCreateRequest req, Authentication auth){
        String email = auth.getName();
        Usuario autor = usuarios.findByEmail(email).orElseThrow();
        Curso curso = cursos.findByNomeIgnoreCase(req.nomeCurso()).orElseGet(() -> cursos.save(new Curso(null, req.nomeCurso())));
        Topico t = new Topico(null, req.titulo(), req.mensagem(), null, curso, autor);
        return toResponse(topicos.save(t));
    }

    public TopicoResponse update(Long id, TopicoUpdateRequest req, Authentication auth){
        Topico t = topicos.findById(id).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Tópico não encontrado"));
        ensureAuthor(t, auth);
        if (req.titulo()!=null) t.setTitulo(req.titulo());
        if (req.mensagem()!=null) t.setMensagem(req.mensagem());
        return toResponse(topicos.save(t));
    }

    public void delete(Long id, Authentication auth){
        Topico t = topicos.findById(id).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Tópico não encontrado"));
        ensureAuthor(t, auth);
        topicos.delete(t);
    }

    private void ensureAuthor(Topico t, Authentication auth){
        String email = auth.getName();
        if (!t.getAutor().getEmail().equals(email)) throw new ForbiddenOperationException("Somente o autor pode alterar/excluir");
    }

    private TopicoResponse toResponse(Topico t){
        return new TopicoResponse(
            t.getId(), t.getTitulo(), t.getMensagem(), t.getDataCriacao(),
            t.getCurso().getId(), t.getCurso().getNome(),
            t.getAutor().getId(), t.getAutor().getNome()
        );
    }
}
