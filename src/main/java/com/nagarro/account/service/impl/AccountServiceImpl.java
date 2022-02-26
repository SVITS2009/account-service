package com.nagarro.account.service.impl;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.InvalidInputException;
import com.nagarro.account.exception.StatementNotFoundException;
import com.nagarro.account.repository.AccountRepository;
import com.nagarro.account.service.AccountService;
import com.nagarro.account.util.AccountUtil;
import com.nagarro.account.util.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<StatementResponse> searchStatement(
            String accountId, String fromDate, String toDate, String fromAmount, String toAmount)
            throws StatementNotFoundException, InvalidInputException {

        if (accountId == null && fromDate == null && toDate == null && fromAmount == null && toAmount == null) {
            return searchStatement();
        }
        // Validating accountId
        Integer id = ValidationHandler.validateAccountId(accountId);

        // Validating fromDate and toDate
        ValidationHandler.validateFromAndToDate(fromDate, toDate);

        // Validating fromAmount and toAmount
        ValidationHandler.validateFromAndToAmount(fromAmount, toAmount);

        List<StatementResponse> statementResponseList = accountRepository.searchStatement(id);
        statementResponseList = AccountUtil.filterBasedOnDate(statementResponseList, fromDate, toDate);
        statementResponseList = AccountUtil.filterBasedOnAmount(statementResponseList, fromAmount, toAmount);
        return statementResponseList;
    }

    @Override
    public List<StatementResponse> searchStatement() throws StatementNotFoundException {
        Format f = new SimpleDateFormat("dd.MM.yyyy");
        String fromDate = f.format(new Date());
        Date date = java.sql.Date.valueOf(LocalDate.now().minus(3, ChronoUnit.MONTHS));
        String toDate = f.format(date);
        List<StatementResponse> statementResponseList = accountRepository.searchStatement(null);
        return AccountUtil.filterBasedOnDate(statementResponseList, fromDate, toDate);
    }
}
