package org.example.exception;

public class NotInServiceException extends RuntimeException{
    public NotInServiceException(String message){
        super(message);
    }
}
