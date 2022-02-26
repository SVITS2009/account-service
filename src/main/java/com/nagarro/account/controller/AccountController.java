package com.nagarro.account.controller;


import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.InvalidInputException;
import com.nagarro.account.exception.StatementNotFoundException;
import com.nagarro.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/search")
    public ResponseEntity<List<StatementResponse>> getLast3MonthsAccountStatements() throws StatementNotFoundException {
        log.info("Executing getLast3MonthsAccountStatements api");
        return new ResponseEntity<>(accountService.searchStatement(), HttpStatus.OK);
    }

    @GetMapping("/search/filter")
    public ResponseEntity<List<StatementResponse>> getAccountStatements(
            @RequestParam(name = "accountId", required = false) String accountId,
            @RequestParam(name = "fromDate", required = false) String fromDate,
            @RequestParam(name = "toDate", required = false) String toDate,
            @RequestParam(name = "fromAmount", required = false) String fromAmount,
            @RequestParam(name = "toAmount", required = false) String toAmount)
            throws InvalidInputException, StatementNotFoundException {

        log.info("Executing getAccountStatements api");
        return new ResponseEntity<>(accountService
                .searchStatement(accountId, fromDate, toDate, fromAmount, toAmount), HttpStatus.OK);
    }
}
