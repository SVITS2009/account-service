package com.nagarro.account.repository.impl;

import com.nagarro.account.constant.Constants;
import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.NotFoundException;
import com.nagarro.account.repository.AccountRepository;
import com.nagarro.account.util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of AccountRepository
 * This class implements AccountRepository  API
 * This class is responsible to interact with DB and fetch the record
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param accountId is used to fetch specific account statements
     * @return list of statementResponse
     * @throws NotFoundException throws this exception when DB doesn't return any record for given accountId
     */
    @Override
    public List<StatementResponse> searchStatement(Integer accountId) throws NotFoundException {
        List<StatementResponse> statementResponseList;
        try {
            statementResponseList = jdbcTemplate.queryForObject(Constants.SELECT_COMMAND_BY_ID,
                    (rs, rowNum) -> AccountUtil.convertResultSet2AccountList(rs), accountId);
            return statementResponseList;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Statement(s) doesn't exist");
        }
    }

    /**
     * To check if DB has any record for given accountId
     * @param accountId to check if accountId is present in DB or not
     * @return true|false
     */
    @Override
    @SuppressWarnings("java:S1874")
    public boolean isAccountExist(Integer accountId) {
        int count = jdbcTemplate.queryForObject(Constants.SELECT_COMMAND_ACCOUNT_ID,new Object[] {accountId},  Integer.class);
        return count >0;
    }
}
