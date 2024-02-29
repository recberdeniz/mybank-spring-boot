package com.mybankapp.mybank.dto.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;

@Component
public class CustomerDtoConverter {

	private final CustomerAccountDtoConverter converter;
	
	
	public CustomerDtoConverter(CustomerAccountDtoConverter converter) {

		this.converter = converter;
	}


	public CustomerDto convert(Customer from) {
		return new CustomerDto(from.getId(), 
				from.getName(),
				from.getDateOfBirth(),
				City.valueOf(from.getCity().name()),
				from.getAddress(),
				from.getAccountList().stream().map(converter::convert).collect(Collectors.toList()));
	}
}
