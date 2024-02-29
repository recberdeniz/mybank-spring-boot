package com.mybankapp.mybank.dto;

import com.mybankapp.mybank.model.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {

	private String id;
	
	private Double balance;
	private Currency currency;
	private AccountCustomerDto customer;
}
