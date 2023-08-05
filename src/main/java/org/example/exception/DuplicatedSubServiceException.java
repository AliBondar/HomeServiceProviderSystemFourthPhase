package org.example.exception;

public class DuplicatedSubServiceException extends RuntimeException {

    public DuplicatedSubServiceException(String message) {
        super(message);
    }
}
