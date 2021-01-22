package com.project.exception;

public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException(String s) {
        super(s);
    }
}
