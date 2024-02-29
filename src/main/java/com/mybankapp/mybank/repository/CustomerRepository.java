package com.mybankapp.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mybankapp.mybank.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
