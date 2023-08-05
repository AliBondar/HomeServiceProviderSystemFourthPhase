package org.example.exception;

public class InvalidTimeException extends RuntimeException{
    public InvalidTimeException(String message){
        super(message);
    }
}
