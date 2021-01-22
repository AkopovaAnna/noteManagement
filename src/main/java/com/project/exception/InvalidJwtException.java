package com.project.exception;

public class InvalidJwtException extends RuntimeException {

    public InvalidJwtException(String message, Exception e) {
        super(message,e);
    }
}
