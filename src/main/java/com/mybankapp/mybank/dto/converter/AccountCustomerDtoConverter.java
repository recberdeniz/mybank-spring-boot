package com.mybankapp.mybank.dto.converter;

import org.springframework.stereotype.Component;

import com.mybankapp.mybank.dto.AccountCustomerDto;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;

@Component
public class AccountCustomerDtoConverter {

	public AccountCustomerDto convert(Customer from) {
		return new AccountCustomerDto(from.getId(),
				from.getName(),
				from.getDateOfBirth(),
				City.valueOf(from.getCity().name()),
				from.getAddress());
	}
}
