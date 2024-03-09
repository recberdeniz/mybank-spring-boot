package com.mybankapp.mybank.dto.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateAccountRequest{

	@Min(0)
	@NotNull(message = "Amount must not be null")
	private Double amount;
}
