package com.alura.forumhub.security;

import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;
    public CustomUserDetailsService(UsuarioRepository repo){ this.repo = repo; }
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.withUsername(u.getEmail()).password(u.getSenha()).roles("USER").build();
    }
}
