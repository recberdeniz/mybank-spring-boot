package com.mybankapp.mybank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybankapp.mybank.IntegrationTestSupport;
import com.mybankapp.mybank.dto.AccountCustomerDto;
import com.mybankapp.mybank.dto.AccountDto;
import com.mybankapp.mybank.dto.requests.CreateAccountRequest;
import com.mybankapp.mybank.dto.requests.UpdateAccountRequest;
import com.mybankapp.mybank.model.Account;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Currency;
import com.mybankapp.mybank.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "server-port=0",
                "command.line.runner.enabled=false"
        })
@RunWith(SpringRunner.class)
@DirtiesContext
public class AccountControllerTest extends IntegrationTestSupport {

    @Test
    public void whenCreateAccountWithValidRequestAndExistCustomerId_ItShouldReturnAccountDto() throws Exception {
        Customer customer = customerRepository.save(generateCustomer(null, "test",
                2000, City.ANKARA, "address"));

        CreateAccountRequest request = generateCreateAccountRequest("USD");

        mockMvc.perform(post(ACCOUNT_API_ENDPOINT + "create/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currency", is("USD")))
                .andExpect(jsonPath("$.customer.id", is(customer.getId())))
                .andReturn();

    }

    @Test
    public void whenCreateAccountWithNullCurrencyRequestAndExistingCustomerId_ItShouldReturnBadRequest()
        throws Exception{
        Customer customer = customerRepository.save(generateCustomer(null, "test",
                2000, City.ANKARA, "address"));

        CreateAccountRequest request = generateCreateAccountRequest("");
        mockMvc.perform(post(ACCOUNT_API_ENDPOINT + "create/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void whenDepositAccountWithInvalidAmountRequest_ItShouldReturnBadRequest() throws Exception{
        Customer customer = customerRepository.save(generateCustomer(null, "test",
                2000, City.ANKARA, "address"));
        Account account = accountRepository.save(generateAccount(customer,
                null, 0.0, Currency.USD));
        UpdateAccountRequest request = generateUpdateAccountRequest(-100.0);
        mockMvc.perform(put(ACCOUNT_API_ENDPOINT + "deposit/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void whenWithdrawAccountWithInvalidAmountRequest_ItShouldReturnBadRequest() throws Exception{
        Customer customer = customerRepository.save(generateCustomer(null, "test",
                2000, City.ANKARA, "address"));
        Account account = accountRepository.save(generateAccount(customer,
                null, 0.0, Currency.USD));
        UpdateAccountRequest request = generateUpdateAccountRequest(-100.0);
        mockMvc.perform(put(ACCOUNT_API_ENDPOINT + "withdraw/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }



}