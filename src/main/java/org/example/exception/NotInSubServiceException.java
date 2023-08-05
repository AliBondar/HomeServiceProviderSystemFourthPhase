package org.example.exception;

public class NotInSubServiceException extends RuntimeException{
    public NotInSubServiceException(String message){
        super(message);
    }
}
