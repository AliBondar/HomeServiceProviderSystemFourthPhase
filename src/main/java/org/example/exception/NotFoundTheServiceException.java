package org.example.exception;

public class NotFoundTheServiceException extends RuntimeException{
    public NotFoundTheServiceException(String message){
        super(message);
    }
}
