package com.alura.forumhub.web;

import com.alura.forumhub.dto.topico.*;
import com.alura.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private final TopicoService service;
    public TopicoController(TopicoService s){ this.service = s; }

    @GetMapping
    public Page<TopicoResponse> list(@RequestParam(value="curso", required=false) String curso,
                                     @RequestParam(value="page", defaultValue="0") int page,
                                     @RequestParam(value="size", defaultValue="10") int size){
        return service.list(curso, PageRequest.of(page, size, Sort.by("dataCriacao").descending()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<TopicoResponse> create(@RequestBody @Valid TopicoCreateRequest req,
                                                 Authentication auth, UriComponentsBuilder uri){
        TopicoResponse r = service.create(req, auth);
        return ResponseEntity.created(uri.path("/topicos/{id}").buildAndExpand(r.id()).toUri()).body(r);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> update(@PathVariable Long id, @RequestBody @Valid TopicoUpdateRequest req,
                                                 Authentication auth){
        return ResponseEntity.ok(service.update(id, req, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth){
        service.delete(id, auth);
        return ResponseEntity.noContent().build();
    }
}
