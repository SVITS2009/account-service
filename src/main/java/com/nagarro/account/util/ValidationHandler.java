package com.nagarro.account.util;

import com.nagarro.account.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ValidationHandler {

    public static Integer validateAccountId(String id) throws InvalidInputException {
        if (StringUtils.isNotEmpty(id)) {
            try {
                return Integer.parseInt(id);
            } catch (NumberFormatException nfe) {
                throw new InvalidInputException("Id is incorrect");
            }
        }
        return null;
    }

    private static boolean validateDateFormat(String date) {
        if(date.length() == 10){
            date = date.replace('.', '_');
            String [] dateArr = date.split("_");
            if (dateArr.length != 3) {
                return false;
            }
            String day = dateArr[0];
            if(day.length() == 2) {
                String month = dateArr[1];
                return month.length() == 2;
            }
        }
        return false;
    }

    public static void validateFromAndToDate(String fromDate, String toDate) throws InvalidInputException {
        if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
            boolean fromDateResult = ValidationHandler.validateDateFormat(fromDate);
            boolean toDateResult = ValidationHandler.validateDateFormat(toDate);
            if (!fromDateResult) {
                throw new InvalidInputException("fromDate format is incorrect, please input as (dd.MM.yyyy)");
            }
            if (!toDateResult) {
                throw new InvalidInputException("toDate format is incorrect, , please input as (dd.MM.yyyy)");
            }
            var formatter = new SimpleDateFormat("dd.MM.yyyy");
            try{
                var startDate = formatter.parse(fromDate);
                var endDate = formatter.parse(toDate);
                if(startDate.after(endDate)) {
                    throw new InvalidInputException("fromDate value is invalid, " +
                            "fromDate value should be less than or equal from toDate value");
                }
            } catch (ParseException ignored) {
                throw new InvalidInputException("Date is invalid, Unable to parse it");
            }
        }
    }

    public static void validateFromAndToAmount(String fromAmount, String toAmount) throws InvalidInputException {
        if (StringUtils.isNotEmpty(fromAmount) && StringUtils.isNotEmpty(toAmount)) {
            boolean fromAmountResult = ValidationHandler.validateAmount(fromAmount);
            boolean toAmountResult = ValidationHandler.validateAmount(toAmount);
            if (!fromAmountResult) {
                throw new InvalidInputException("fromAmount value is invalid");
            }
            if (!toAmountResult) {
                throw new InvalidInputException("toAmount value is invalid");
            }
            var startAmount = Double.parseDouble(fromAmount);
            var endAmount = Double.parseDouble(toAmount);
            if(startAmount > endAmount) {
                throw new InvalidInputException("fromAmount value is invalid, " +
                        "fromAmount value should be less than or equal from toAmount value");
            }
        }
    }

    private static boolean validateAmount(String amount) {
        try {
            Double.parseDouble(amount);
        }catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
