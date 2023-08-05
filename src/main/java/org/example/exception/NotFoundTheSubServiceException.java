package org.example.exception;

public class NotFoundTheSubServiceException extends RuntimeException{
    public NotFoundTheSubServiceException(String message){
        super(message);
    }
}
