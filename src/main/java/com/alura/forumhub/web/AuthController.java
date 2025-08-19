package com.alura.forumhub.web;

import com.alura.forumhub.dto.auth.LoginRequest;
import com.alura.forumhub.dto.auth.RegisterRequest;
import com.alura.forumhub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService s){ this.service = s; }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest req){
        service.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest req){
        return ResponseEntity.ok(service.login(req));
    }
}
