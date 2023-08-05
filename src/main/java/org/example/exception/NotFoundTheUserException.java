package org.example.exception;

public class NotFoundTheUserException extends RuntimeException {
    public NotFoundTheUserException(String message) {
        super(message);
    }
}
