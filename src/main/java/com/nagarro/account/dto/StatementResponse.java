package com.nagarro.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class StatementResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer accountId;

    private Integer statementId;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("datefield")
    private String date;

    @JsonProperty("amount")
    private String amount;
}
