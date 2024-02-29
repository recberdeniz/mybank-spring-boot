package com.mybankapp.mybank.dto.converter;

import org.springframework.stereotype.Component;

import com.mybankapp.mybank.dto.AccountDto;
import com.mybankapp.mybank.model.Account;
import com.mybankapp.mybank.model.Currency;

@Component
public class AccountDtoConverter {

	private final AccountCustomerDtoConverter converter;

	public AccountDtoConverter(AccountCustomerDtoConverter converter) {
		this.converter = converter;
	}
	
	public AccountDto convert(Account from) {
		return new AccountDto(from.getId(),
				from.getBalance(),
				Currency.valueOf(from.getCurrency().name()),
				converter.convert(from.getCustomer()));
	}
	
}
