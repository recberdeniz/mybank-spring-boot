package com.mybankapp.mybank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybankapp.mybank.dto.converter.AccountCustomerDtoConverter;
import com.mybankapp.mybank.dto.converter.CustomerDtoConverter;
import com.mybankapp.mybank.repository.AccountRepository;
import com.mybankapp.mybank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class IntegrationTestSupport extends TestSupport{

    protected static final ObjectMapper om = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected CustomerDtoConverter customerDtoConverter;

    @Autowired
    protected AccountCustomerDtoConverter accountCustomerDtoConverter;

    protected static final String CUSTOMER_API_ENDPOINT = "/v1/customer/";
    protected static final String ACCOUNT_API_ENDPOINT = "/v1/account/";

}
