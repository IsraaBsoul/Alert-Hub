package com.example.Evaluation.exception;

public class DeveloperNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DeveloperNotFoundException(String message) {
        super(message);
    }

    public DeveloperNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
