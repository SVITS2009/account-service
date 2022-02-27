package com.nagarro.account.repository;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.NotFoundException;

import java.util.List;

/**
 * Implements AccountRepository to declare
 * repository API which interacts with DB
 */
public interface AccountRepository {
    List<StatementResponse> searchStatement(Integer accountId) throws NotFoundException;

    boolean isAccountExist(Integer accountId);
}
