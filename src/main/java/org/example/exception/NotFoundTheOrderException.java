package org.example.exception;

public class NotFoundTheOrderException extends RuntimeException{
    public NotFoundTheOrderException(String message){
        super(message);
    }
}
