package com.hunter.fota.aspect;

import com.hunter.fota.exception.EntityExistsException;
import com.hunter.fota.exception.EntityNotFoundException;
import com.hunter.fota.exception.LogicalErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String onEntityNotFoundException(EntityNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String onEntityExistsException(EntityExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String onConstraintViolationException(ConstraintViolationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(LogicalErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String onLogicalErrorException(LogicalErrorException e) {
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String onException(Exception e) {
        return e.getMessage();
    }


}
