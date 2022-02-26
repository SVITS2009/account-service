package com.nagarro.account.util;

import com.nagarro.account.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
                if(month.length() == 2) {
                    return true;
                }
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
