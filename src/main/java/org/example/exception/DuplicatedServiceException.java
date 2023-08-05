package org.example.exception;

public class DuplicatedServiceException extends RuntimeException{

    public DuplicatedServiceException(String message){
        super(message);
    }
}
