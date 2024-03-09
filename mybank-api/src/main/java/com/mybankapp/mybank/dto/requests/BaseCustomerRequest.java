package com.mybankapp.mybank.dto.requests;

import com.mybankapp.mybank.model.City;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class BaseCustomerRequest {

	@NotNull(message = "City must not be null")
	private String city;
	@NotNull(message = "Address must not be null")
	private String address;
}
