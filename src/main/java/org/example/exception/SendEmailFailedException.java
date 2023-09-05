package org.example.exception;

public class SendEmailFailedException extends RuntimeException{
    public SendEmailFailedException(String message){
        super(message);
    }
}
