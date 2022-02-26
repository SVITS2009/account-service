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

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandler(Exception ex, WebRequest request) {
        String detail = request.getDescription(false);
        List<String> details = new ArrayList<>();
        details.add(detail);
        ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), details);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(StatementNotFoundException.class)
    public ResponseEntity<Object> statementNotFoundException(StatementNotFoundException ex, WebRequest request) {
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

    /*@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(new Date(), "Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }*/
}
