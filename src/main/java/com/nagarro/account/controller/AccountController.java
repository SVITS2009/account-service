package com.nagarro.account.controller;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.InvalidInputException;
import com.nagarro.account.exception.NotFoundException;
import com.nagarro.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implements AccountController to add APIs
 */
@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     *
     * @param accountId is used to fetch specific account statements
     * @param fromDate is used for date filter on record which are getting using accountId from DB
     * @param toDate   is used for date filter on record which are getting using accountId from DB
     * @param fromAmount is used for amount filter on record which are getting using accountId from DB
     * @param toAmount is used for date amount on record which are getting using accountId from DB
     * @return
     * @throws InvalidInputException throws this exception when input is invalid like accountId, fromDate, toDate
     * fromAmount and toAmount
     * @throws NotFoundException throws this exception when accountId and statements don't exist in DB.
     */
    @GetMapping("/search/{accountId}")
    public ResponseEntity<List<StatementResponse>> getAccountStatements(
            @PathVariable(name = "accountId") String accountId,
            @RequestParam(name = "fromDate", required = false) String fromDate,
            @RequestParam(name = "toDate", required = false) String toDate,
            @RequestParam(name = "fromAmount", required = false) String fromAmount,
            @RequestParam(name = "toAmount", required = false) String toAmount)
            throws InvalidInputException, NotFoundException {

        log.info("Executing getAccountStatements api");
        return new ResponseEntity<>(accountService
                .searchStatement(accountId, fromDate, toDate, fromAmount, toAmount), HttpStatus.OK);
    }
}
