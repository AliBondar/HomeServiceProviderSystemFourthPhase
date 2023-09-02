package org.example.controller;

import org.example.exception.EmptyFieldException;
import org.example.exception.InvalidTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<String> handleSomeException(EmptyFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty field.");
    }

    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<String> handleAnotherException(InvalidTimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
