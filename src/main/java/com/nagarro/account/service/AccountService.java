package com.nagarro.account.service;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.InvalidInputException;
import com.nagarro.account.exception.NotFoundException;

import java.util.List;

/**
 * Implements AccountService to declare
 * service API which interacts with repository
 */
public interface AccountService {
    List<StatementResponse> searchStatement(
            String accountId, String fromDate, String toDate, String fromAmount, String toAmount)
            throws NotFoundException, InvalidInputException;
}
