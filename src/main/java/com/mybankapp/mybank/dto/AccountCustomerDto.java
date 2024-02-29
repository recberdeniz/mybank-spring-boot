package com.mybankapp.mybank.dto;

import com.mybankapp.mybank.model.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountCustomerDto {

	private String id;
	private String name;
	private Integer dateOfBirth;
	private City city;
	private String address;
}
