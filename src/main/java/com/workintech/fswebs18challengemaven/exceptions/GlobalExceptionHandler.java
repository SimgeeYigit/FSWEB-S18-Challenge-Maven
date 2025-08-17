package com.workintech.fswebs18challengemaven.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleException(CardException cardException) {
        log.error("Burger exception occured! Exception details: ", cardException);
        CardErrorResponse errorResponse = new CardErrorResponse(
                cardException.getMessage());
        return new ResponseEntity<>(errorResponse, cardException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleException(Exception exception) {
        CardErrorResponse errorResponse = new CardErrorResponse(
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
