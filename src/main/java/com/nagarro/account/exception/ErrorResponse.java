package com.nagarro.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private Date timestamp;
    private String message;
    private List<String> details;
}
