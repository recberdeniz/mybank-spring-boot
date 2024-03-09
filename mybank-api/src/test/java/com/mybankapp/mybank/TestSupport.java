package com.mybankapp.mybank;

import com.mybankapp.mybank.dto.requests.CreateAccountRequest;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.dto.requests.UpdateAccountRequest;
import com.mybankapp.mybank.dto.requests.UpdateCustomerRequest;
import com.mybankapp.mybank.model.Account;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Currency;
import com.mybankapp.mybank.model.Customer;

import java.util.ArrayList;


public class TestSupport {

    public Customer generateCustomer(String id, String name, Integer dateOfBirth, City city, String address){
        return new Customer(id, name, dateOfBirth, city, address, new ArrayList<>());
    }

    public CreateCustomerRequest generateCreateCustomerRequest(String name, Integer dateOfBirth,
                                                         String city, String address){
        CreateCustomerRequest customerRequest = new CreateCustomerRequest();
        customerRequest.setName(name);
        customerRequest.setDateOfBirth(dateOfBirth);
        customerRequest.setCity(city);
        customerRequest.setAddress(address);

        return customerRequest;
    }

    public UpdateCustomerRequest generateUpdateCustomerRequest(String city, String address){
        UpdateCustomerRequest customerRequest = new UpdateCustomerRequest();
        customerRequest.setCity(city);
        customerRequest.setAddress(address);

        return customerRequest;
    }


    public Account generateAccount(Customer customer, String id, Double balance, Currency currency){

        return new Account(id, balance, currency, customer);
    }

    public CreateAccountRequest generateCreateAccountRequest(String currency){
        CreateAccountRequest accountRequest = new CreateAccountRequest();
        accountRequest.setCurrency(currency);
        return accountRequest;
    }

    public UpdateAccountRequest generateUpdateAccountRequest(Double amount){
        UpdateAccountRequest accountRequest = new UpdateAccountRequest();
        accountRequest.setAmount(amount);
        return accountRequest;
    }
}
