package com.nagarro.account.repository.impl;

import com.nagarro.account.constant.Constants;
import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.StatementNotFoundException;
import com.nagarro.account.repository.AccountRepository;
import com.nagarro.account.util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<StatementResponse> searchStatement(Integer accountId) throws StatementNotFoundException {
        List<StatementResponse> statementResponseList;
        try {
            if (accountId == null) {
                statementResponseList = jdbcTemplate.queryForObject(Constants.SELECT_COMMAND,
                        (rs, rowNum) -> AccountUtil.convertResultSet2AccountList(rs));
            } else {
                statementResponseList = jdbcTemplate.queryForObject(Constants.SELECT_COMMAND_BY_ID,
                        (rs, rowNum) -> AccountUtil.convertResultSet2AccountList(rs), accountId);
            }
            return statementResponseList;
        } catch (EmptyResultDataAccessException e) {
            throw new StatementNotFoundException("Statement(s) doesn't exist");
        }
    }
}
