package com.nagarro.account.util;

import com.nagarro.account.dto.StatementResponse;
import com.nagarro.account.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implements AccountUtil class to keep all common API
 * which can be use in all classes.
 * This class has functions like filter based on Date and Amount, masked accountNumber
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class AccountUtil {

    @SuppressWarnings("java:S108")
    public static List<StatementResponse> filterBasedOnDate(
            List<StatementResponse> statementResponseList, String fromDate, String toDate) {

        if(StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) {
            return statementResponseList;
        }
        List<StatementResponse> list = new ArrayList<>();
        var formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            var startDate = formatter.parse(fromDate);
            var endDate = formatter.parse(toDate);
            for (StatementResponse statementResponse : statementResponseList) {
                var accountDate = formatter.parse(statementResponse.getDate());
                if((accountDate.equals(startDate) || accountDate.equals(endDate))
                        || (accountDate.after(startDate) && accountDate.before(endDate))) {
                    list.add(statementResponse);
                }
            }
        } catch (ParseException ignored) {
        }
        return list;
    }

    public static List<StatementResponse> filterBasedOnAmount(List<StatementResponse> statementResponseList,
                                                              String fromAmount, String toAmount) {
        if(StringUtils.isEmpty(fromAmount) || StringUtils.isEmpty(toAmount)) {
            return statementResponseList;
        }
        Double startAmount = Double.parseDouble(fromAmount);
        Double endAmount = Double.parseDouble(toAmount);
        List<StatementResponse> list = new ArrayList<>();
        for (StatementResponse statementResponse : statementResponseList) {
            Double accountAmount = Double.parseDouble(statementResponse.getAmount());
            if((Objects.equals(accountAmount, startAmount) || Objects.equals(accountAmount, endAmount))
            || (accountAmount > startAmount && accountAmount < endAmount)) {
                list.add(statementResponse);
            }
        }
        return list;
    }

    private static String maskedAccountNumber(String accountNumber) {
        return accountNumber.replaceAll("\\d+", "*************");
    }

    public static List<StatementResponse> convertResultSet2AccountList(ResultSet rs) throws SQLException {
        List<StatementResponse> statementResponseList = new ArrayList<>();
        while(rs.next()) {
            var statementResponse = new StatementResponse();
            statementResponse.setAccountId(rs.getInt("accountId"));
            statementResponse.setStatementId(rs.getInt("statementId"));
            statementResponse.setAccountType(rs.getString("account_type"));
            statementResponse.setAccountNumber(maskedAccountNumber(rs.getString("account_number")));
            statementResponse.setDate(rs.getString("date"));
            statementResponse.setAmount(rs.getString("amount"));
            statementResponseList.add(statementResponse);
        }
        return statementResponseList;
    }

    public static List<StatementResponse> isListEmpty(List<StatementResponse> statementResponseList) throws NotFoundException {
        if(statementResponseList.isEmpty()) {
            throw new NotFoundException("Statement(s) not exist");
        }
        return statementResponseList;
    }
}
