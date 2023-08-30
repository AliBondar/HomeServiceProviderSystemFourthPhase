package org.example.exception;

public class DuplicatedScoreException extends RuntimeException{
    public DuplicatedScoreException(String message){
        super(message);
    }
}
