package com.nagarro.account.service.impl;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.InvalidInputException;
import com.nagarro.account.exception.NotFoundException;
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

/**
 * Implementation of AccountService
 * This class implements AccountService  API
 * This class is responsible to perform all validation on input param, Filter the
 * account statements based on Date and amount filter.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

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
    @Override
    public List<StatementResponse> searchStatement(
            String accountId, String fromDate, String toDate, String fromAmount, String toAmount)
            throws NotFoundException, InvalidInputException {

        // Validating accountId
        Integer id = ValidationHandler.validateAccountId(accountId);
        if(!accountRepository.isAccountExist(id)) {
            throw new NotFoundException("AccountId doesn't exist");
        }

        if (fromDate == null && toDate == null && fromAmount == null && toAmount == null) {
            return AccountUtil.isListEmpty(getLast3MonthsAccountStatements(id));
        }

        // Validating fromDate and toDate
        ValidationHandler.validateFromAndToDate(fromDate, toDate);

        // Validating fromAmount and toAmount
        ValidationHandler.validateFromAndToAmount(fromAmount, toAmount);

        List<StatementResponse> statementResponseList = accountRepository.searchStatement(id);

        // Applying date filter
        statementResponseList = AccountUtil.filterBasedOnDate(statementResponseList, fromDate, toDate);

        //Applying amount filter
        statementResponseList = AccountUtil.filterBasedOnAmount(statementResponseList, fromAmount, toAmount);

        return AccountUtil.isListEmpty(statementResponseList);
    }

    /**
     *
     * @param id to fetch specific accountId statements
     * @return list of statements
     * @throws NotFoundException if accountId doesn't have that
     */
    private List<StatementResponse> getLast3MonthsAccountStatements(Integer id) throws NotFoundException {
        Format f = new SimpleDateFormat("dd.MM.yyyy");
        String fromDate = f.format(new Date());
        Date date = java.sql.Date.valueOf(LocalDate.now().minus(3, ChronoUnit.MONTHS));
        String toDate = f.format(date);
        List<StatementResponse> statementResponseList = accountRepository.searchStatement(id);
        return AccountUtil.filterBasedOnDate(statementResponseList, fromDate, toDate);
    }
}
