package com.alura.forumhub.dto.auth;

public record TokenResponse(String token, String type){
    public TokenResponse(String t){ this(t, "Bearer"); }
}
