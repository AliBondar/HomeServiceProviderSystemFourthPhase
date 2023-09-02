package org.example.controller;

import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> handleException(DuplicatedEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
    }

    @ExceptionHandler(DuplicatedScoreException.class)
    public ResponseEntity<String> handleException(DuplicatedScoreException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicated Score is not allowed");
    }

    @ExceptionHandler(DuplicatedServiceException.class)
    public ResponseEntity<String> handleException(DuplicatedServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service already exists");
    }

    @ExceptionHandler(DuplicatedSubServiceException.class)
    public ResponseEntity<String> handleException(DuplicatedSubServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sub service already exists");
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<String> handleException(EmptyFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty field.");
    }

    @ExceptionHandler(ImageSizeException.class)
    public ResponseEntity<String> handleException(ImageSizeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image size must be less than 300kb");
    }

    @ExceptionHandler(ImageFormatException.class)
    public ResponseEntity<String> handleException(ImageFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image format must be jpg");
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> handleException(InvalidDateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date");
    }

    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<String> handleAnotherException(InvalidTimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid time");
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleException(InvalidEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleException(InvalidPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is invalid. It must contain at least eight characters, one special character, Capital digit and number");
    }


    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<String> handleException(InvalidPriceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid price");
    }

    @ExceptionHandler(NotFoundTheOfferException.class)
    public ResponseEntity<String> handleException(NotFoundTheOfferException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found the offer");
    }

    @ExceptionHandler(NotFoundTheOrderException.class)
    public ResponseEntity<String> handleException(NotFoundTheOrderException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found the order");
    }

    @ExceptionHandler(NotFoundTheScoreException.class)
    public ResponseEntity<String> handleException(NotFoundTheScoreException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found the score");
    }

    @ExceptionHandler(NotFoundTheServiceException.class)
    public ResponseEntity<String> handleException(NotFoundTheServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found the service");
    }

    @ExceptionHandler(NotFoundTheSubServiceException.class)
    public ResponseEntity<String> handleException(NotFoundTheSubServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found the sub service");
    }

    @ExceptionHandler(NotFoundTheUserException.class)
    public ResponseEntity<String> handleException(NotFoundTheUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found the user");
    }

    @ExceptionHandler(NotInServiceException.class)
    public ResponseEntity<String> handleException(NotInServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not allowed in this service");
    }

    @ExceptionHandler(NotInSubServiceException.class)
    public ResponseEntity<String> handleException(NotInSubServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not allowed in this sub service");
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity<String> handleException(OrderStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order is not appropriate status");
    }

    @ExceptionHandler(ScoreRangeException.class)
    public ResponseEntity<String> handleException(ScoreRangeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid score. It must be between 1 and 5");
    }

    @ExceptionHandler(UserConfirmationException.class)
    public ResponseEntity<String> handleException(UserConfirmationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User in not confirmed yet");
    }

    @ExceptionHandler(WalletBalanceException.class)
    public ResponseEntity<String> handleException(WalletBalanceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wallet balance is not enough");
    }

}
