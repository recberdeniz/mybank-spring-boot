package com.mybankapp.mybank.dto;


import java.util.List;

import com.mybankapp.mybank.model.City;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

	
	private String id;
	private String name;
	private Integer dateOfBirth;
	private City city;
	private String address;
	
	private List<CustomerAccountDto> accountList;
}
