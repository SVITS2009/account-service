package com.nagarro.account.controller;

import com.nagarro.account.exception.CustomExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class AccountControllerTest {
    private static final String PATH = "/account/search";

    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testGetLast3MonthsAccountStatements() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAccountStatementsWithValidAccountID() throws Exception {
        mockMvc.perform(get(PATH + "/filter?accountId=2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAccountStatementsWithInvalidAccountID() throws Exception {
        mockMvc.perform(get(PATH + "/filter?accountId=manish").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAccountStatementsWithEmptyAccountID() throws Exception {
        mockMvc.perform(get(PATH + "/filter?accountId=").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testStatementNotFound() throws Exception {
        mockMvc.perform(get(PATH + "/filter?accountId=6").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAccountStatementsWithoutInput() throws Exception {
        mockMvc.perform(get(PATH + "/filter").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /*
       Below Testcases are covered fromAmount and toAmount related code coverage
     */
    @Test
    void testFromAmountWithInvalidValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=manish&toAmount=300.1234566543323").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testToAmountWithInvalidValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=3.12345612345&toAmount=manish").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAmountWithValidValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=200.712345612345&toAmount=400.4356732345323").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFromAmountWithEmptyValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=&toAmount=400.4356732345323").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testToAmountWithEmptyValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=400.4356732345323&toAmount=").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAmountWithEmptyValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=&toAmount=").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFromAmountWithEqualValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=623.461804295262&toAmount=1000.234566543323").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testToAmountWithEqualValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=623.461804295262&toAmount=967.410308637791").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStatementWithAccountIdAndAmountValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?accountId=3&fromAmount=623.461804295262&toAmount=967.410308637791").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStatementWithFromAmount() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=300.1234543").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStatementWithToAmount() throws Exception {
        mockMvc.perform(get(PATH + "/filter?toAmount=300.87643345656").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFromAmountGreaterToAmount() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromAmount=400&toAmount=200").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /*
       Below Testcases are covered fromDate and toDate related code coverage
     */
    @Test
    void testFromDateWithInvalidValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=manish&toDate=01.01.2000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testToDateWithInvalidValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.01.2000&toDate=manish").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDateWithValidValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.01.2000&toDate=01.01.2022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFromDateWithEmptyValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=&toDate=01.01.2022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testToDateWithEmptyValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.01.2022&toDate=").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDateWithEmptyValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=&toDate=").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFromDateWithEqualValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=09.08.2020&toDate=10.08.2022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testToDateWithEqualValue() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=22.06.2020&toDate=09.08.2020").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testParseException() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.02.mani&toDate=02.02.2022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidInputDateRangeWthWrongFromDateLength() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=1.02.2022&toDate=2.02.2022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidInputDateRangeWthWrongToDateLength() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.02.2022&toDate=2.02.2022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidInputDateRangeWithWrongFormat() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=0102.12022&toDate=0202.12022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidInputDateRangeWithWrongDay() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=1.02.12022&toDate=2.02.12022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidInputDateRangeWithWrongMonth() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.2.12022&toDate=02.2.12022").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetStatementWithFromDate() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=03.09.2020").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetStatementWithToDate() throws Exception {
        mockMvc.perform(get(PATH + "/filter?toDate=19.05.2020").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFromDateGreaterToDate() throws Exception {
        mockMvc.perform(get(PATH + "/filter?fromDate=01.01.2022&toDate=19.05.2020").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
