package com.alura.forumhub.exception;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(String m){ super(m); }
}
