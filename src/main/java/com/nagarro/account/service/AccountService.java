package com.nagarro.account.service;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.InvalidInputException;
import com.nagarro.account.exception.StatementNotFoundException;

import java.util.List;

public interface AccountService {
    List<StatementResponse> searchStatement(
            String accountId, String fromDate, String toDate, String fromAmount, String toAmount)
            throws StatementNotFoundException, InvalidInputException;

    List<StatementResponse> searchStatement() throws StatementNotFoundException;
}
