package com.santana.moneytalk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(TransacaoNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handler(TransacaoNotFound e) {
        return new ErrorMessage(e.getMessage(), 404, LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationErrorMessage> handler(MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(ValidationErrorMessage::new)
                .toList();
    }

}
