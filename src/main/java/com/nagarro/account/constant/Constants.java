package com.nagarro.account.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * implements Constants class to declare all SQL query commands
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String SELECT_COMMAND = "select account.id as accountId, account.account_type, " +
            "account.account_number, statement.id as statementId, statement.datefield as date, " +
            "statement.amount from account inner join " +
            "statement on account.id = statement.account_id";

    public static final String SELECT = "select count(*) from account";

    public static final String WHERE_WITH_ID = " where id = ?";

    public static final String SELECT_COMMAND_BY_ID = SELECT_COMMAND + WHERE_WITH_ID;

    public static final String SELECT_COMMAND_ACCOUNT_ID = SELECT + WHERE_WITH_ID;
}
