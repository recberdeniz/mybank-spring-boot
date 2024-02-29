package com.mybankapp.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mybankapp.mybank.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

}
