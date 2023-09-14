package org.example.controller;

import org.example.dto.response.ProjectResponse;
import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ProjectResponse> handleException(DuplicatedEmailException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicatedScoreException.class)
    public ResponseEntity<ProjectResponse> handleException(DuplicatedScoreException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicatedServiceException.class)
    public ResponseEntity<ProjectResponse> handleException(DuplicatedServiceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicatedSubServiceException.class)
    public ResponseEntity<ProjectResponse> handleException(DuplicatedSubServiceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ProjectResponse> handleException(EmptyFieldException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(ImageSizeException.class)
    public ResponseEntity<ProjectResponse> handleException(ImageSizeException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(ImageFormatException.class)
    public ResponseEntity<ProjectResponse> handleException(ImageFormatException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ProjectResponse> handleException(InvalidDateException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<ProjectResponse> handleAnotherException(InvalidTimeException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ProjectResponse> handleException(InvalidEmailException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ProjectResponse> handleException(InvalidPasswordException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }


    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ProjectResponse> handleException(InvalidPriceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTheOfferException.class)
    public ResponseEntity<ProjectResponse> handleException(NotFoundTheOfferException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTheOrderException.class)
    public ResponseEntity<ProjectResponse> handleException(NotFoundTheOrderException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTheScoreException.class)
    public ResponseEntity<ProjectResponse> handleException(NotFoundTheScoreException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTheServiceException.class)
    public ResponseEntity<ProjectResponse> handleException(NotFoundTheServiceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTheSubServiceException.class)
    public ResponseEntity<ProjectResponse> handleException(NotFoundTheSubServiceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTheUserException.class)
    public ResponseEntity<ProjectResponse> handleException(NotFoundTheUserException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotInServiceException.class)
    public ResponseEntity<ProjectResponse> handleException(NotInServiceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(NotInSubServiceException.class)
    public ResponseEntity<ProjectResponse> handleException(NotInSubServiceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity<ProjectResponse> handleException(OrderStatusException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(ScoreRangeException.class)
    public ResponseEntity<ProjectResponse> handleException(ScoreRangeException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserConfirmationException.class)
    public ResponseEntity<ProjectResponse> handleException(UserConfirmationException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

    @ExceptionHandler(WalletBalanceException.class)
    public ResponseEntity<ProjectResponse> handleException(WalletBalanceException ex) {
        return ResponseEntity.badRequest().body(new ProjectResponse(ex.getMessage()));
    }

}
