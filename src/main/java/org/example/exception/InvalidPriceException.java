package org.example.exception;

public class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(String message){
        super(message);
    }
}
