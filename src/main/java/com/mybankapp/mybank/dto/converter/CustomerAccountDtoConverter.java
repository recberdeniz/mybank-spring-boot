package com.mybankapp.mybank.dto.converter;

import org.springframework.stereotype.Component;

import com.mybankapp.mybank.dto.CustomerAccountDto;
import com.mybankapp.mybank.model.Account;
import com.mybankapp.mybank.model.Currency;

@Component
public class CustomerAccountDtoConverter {

	public CustomerAccountDto convert(Account from) {
		return new CustomerAccountDto(from.getId(), 
				from.getBalance(),
				Currency.valueOf(from.getCurrency().name()));
	}
}
