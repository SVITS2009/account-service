package com.nagarro.account.repository;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.StatementNotFoundException;

import java.util.List;

public interface AccountRepository {
    List<StatementResponse> searchStatement(Integer id) throws StatementNotFoundException;
}
