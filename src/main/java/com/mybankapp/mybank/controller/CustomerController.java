package com.mybankapp.mybank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.dto.requests.UpdateCustomerRequest;
import com.mybankapp.mybank.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {

		this.customerService = customerService;
	}
	
	// Find Customer By id
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getCustomerWithId(@PathVariable String id){
		return ResponseEntity.ok(customerService.getCustomerWithId(id));
	}
	
	// Find All Customers
	@GetMapping
	public ResponseEntity<List<CustomerDto>> getAllCustomers(){
		return ResponseEntity.ok(customerService.getAllCustomers());
	}
	
	@PostMapping("/create")
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest){
		return ResponseEntity.ok(customerService.createCustomer(customerRequest));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody UpdateCustomerRequest customerRequest,
			@PathVariable String id){
		return ResponseEntity.ok(customerService.updateCustomer(customerRequest, id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
		customerService.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}
	
	
	
}
