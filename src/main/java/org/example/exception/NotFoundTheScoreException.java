package org.example.exception;

public class NotFoundTheScoreException extends RuntimeException{
    public NotFoundTheScoreException(String message){
        super(message);
    }
}
