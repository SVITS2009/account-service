package com.nagarro.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  implements InvalidInputException class to throw
 *  exception when input param value is invalid
 *  This class sends 400 as http status code
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message) {
        super(message);
    }
}