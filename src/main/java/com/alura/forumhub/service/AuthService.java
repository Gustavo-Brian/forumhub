package com.alura.forumhub.service;

import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.dto.auth.*;
import com.alura.forumhub.repository.UsuarioRepository;
import com.alura.forumhub.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarios; private final PasswordEncoder enc; private final AuthenticationManager auth; private final JwtTokenProvider jwt;
    public AuthService(UsuarioRepository u, PasswordEncoder e, AuthenticationManager a, JwtTokenProvider j){ this.usuarios=u; this.enc=e; this.auth=a; this.jwt=j; }

    public void register(RegisterRequest req){
        if (usuarios.findByEmail(req.email()).isPresent()) throw new IllegalArgumentException("Email já cadastrado");
        Usuario u = new Usuario(null, req.nome(), req.email(), enc.encode(req.senha()));
        usuarios.save(u);
    }

    public TokenResponse login(LoginRequest req){
        auth.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.senha()));
        String token = jwt.generateToken(req.email());
        return new TokenResponse(token);
    }
}
