package com.andpostman.dependencymanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DependencyExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NullPointerException.class})
    public String emptyFieldsHandler(NullPointerException exception){
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EventExistException.class})
    public String eventAlreadyExistHandler(EventExistException exception){
        log.warn(exception.getMessage());
        return exception.getMessage();
    }
}
