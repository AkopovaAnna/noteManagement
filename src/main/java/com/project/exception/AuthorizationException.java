package com.project.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("AnUnotherized Access");
    }
    public AuthorizationException(String message) {
        super(message);
    }
}
