package com.aydin.trip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            IllegalArgumentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            IllegalStateException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        exception.getMessage(),
                        HttpStatus.CONFLICT.value()
                ));
    }
}