package com.mybankapp.mybank.dto;


import com.mybankapp.mybank.model.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerAccountDto {

	private String id;	
	private Double balance;
	private Currency currency;
}
