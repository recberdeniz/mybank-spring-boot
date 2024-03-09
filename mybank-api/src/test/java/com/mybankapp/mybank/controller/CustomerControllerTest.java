package com.mybankapp.mybank.controller;

import com.mybankapp.mybank.IntegrationTestSupport;
import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;
import org.junit.Before;
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
public class CustomerControllerTest extends IntegrationTestSupport {

    @Before
    public void setUp() throws Exception {

    }
/*
    @Test
    public void findExistCustomerWithId_ShouldReturnResponseOK() throws Exception {

        Customer customer = customerRepository.save(generateCustomer(null,
                "test", 2000, City.ANKARA, "address"));


        mockMvc.perform(get("/v1/customer/" + customer.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer.getId())))
                .andReturn();

    }
*/
    @Test
    public void findExistCustomerWithId_ShouldReturnCustomerDto() throws Exception{
        Customer customer = customerRepository.save(
                generateCustomer("testid", "test", 2000, City.ANKARA, "address")
        );

        CustomerDto expected = customerDtoConverter.convert(customer);

        mockMvc.perform(get(CUSTOMER_API_ENDPOINT + customer.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(om.writer().withDefaultPrettyPrinter().writeValueAsString(expected),
                        false))
                .andExpect(jsonPath("$.id", is(expected.getId())))
                .andReturn();
    }
    @Test
    public void findNonExistCustomerWithId_ShouldReturnHttpNotFound() throws Exception{
        mockMvc.perform(get(CUSTOMER_API_ENDPOINT + "notExistingId"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void createCustomerRequestWithOutCity_ShouldReturnBadRequest() throws Exception{
        CreateCustomerRequest request = generateCreateCustomerRequest("test",
                2000, null, "address");

        mockMvc.perform(post(CUSTOMER_API_ENDPOINT + "create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }



}