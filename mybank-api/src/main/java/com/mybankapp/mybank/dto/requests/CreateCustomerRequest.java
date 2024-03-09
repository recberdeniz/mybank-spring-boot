package com.mybankapp.mybank.dto.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest extends BaseCustomerRequest{

	@NotBlank(message = "Name must not be null!")
	private String name;
	@NotNull(message = "Date of birth must not be null!")
	private Integer dateOfBirth;

}
