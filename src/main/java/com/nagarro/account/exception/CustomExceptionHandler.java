package com.nagarro.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements CustomExceptionHandler to provide customize response
 * when backend API throws any Exception
 * This class methods prepare errorResponse class object with
 * timestamp, message(reason to fail) and details about API like URL
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException ex, WebRequest request) {
        String detail = request.getDescription(false);
        List<String> details = new ArrayList<>();
        details.add(detail);
        var errorResponse = new ErrorResponse(new Date(), ex.getMessage(), details);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> invalidInputException(InvalidInputException ex, WebRequest request) {
        String detail = request.getDescription(false);
        List<String> details = new ArrayList<>();
        details.add(detail);
        var errorResponse = new ErrorResponse(new Date(), ex.getMessage(), details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
