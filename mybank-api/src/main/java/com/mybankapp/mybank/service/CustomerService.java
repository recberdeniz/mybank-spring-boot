package com.mybankapp.mybank.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.converter.CustomerDtoConverter;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.dto.requests.UpdateCustomerRequest;
import com.mybankapp.mybank.exception.InvalidCityException;
import com.mybankapp.mybank.exception.CustomerNotFoundException;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerDtoConverter converter;
	
	
	public CustomerService(CustomerRepository customerRepository,
			CustomerDtoConverter converter) {
		this.customerRepository = customerRepository;
		this.converter = converter;
	}


	public CustomerDto getCustomerWithId(String id) {
		
		return converter.
				convert(
						findCustomerById(id)
						);
	}


	public List<CustomerDto> getAllCustomers() {
		
		return customerRepository.
				findAll().
				stream().
				map(converter::convert).collect(Collectors.toList());
	}


	public CustomerDto createCustomer(CreateCustomerRequest customerRequest) {
		
		City city = checkCityList(customerRequest.getCity());
		Customer customer = customerRepository.save(new Customer(null,
				customerRequest.getName(),
				customerRequest.getDateOfBirth(),
				city,
				customerRequest.getAddress(),
				new ArrayList<>()));
		return converter.convert(customer);
	}
	
	private static City checkCityList(String city) {
		return Arrays.stream(City.values())
				.filter(c -> c.name().equals(city))
				.findFirst()
				.orElseThrow(() ->
				new InvalidCityException("Invalid city name: " + city));
	}


	public CustomerDto updateCustomer(UpdateCustomerRequest customerRequest, String id) {
		Customer customer = findCustomerById(id);
		
		City city = checkCityList(customerRequest.getCity());
		customer.setCity(city);
		customer.setAddress(customerRequest.getAddress());
		Customer updatedCustomer = customerRepository.save(customer);
		return converter.convert(updatedCustomer);
	}
	
	protected Customer findCustomerById(String id) {
		return customerRepository
				.findById(id)
				.orElseThrow(
						() -> new CustomerNotFoundException("Customer could not find with id: " + id)
						);
	}


	public void deleteCustomer(String id) {
		
		Customer customer = findCustomerById(id);
		customerRepository.delete(customer);
	}
	
	

}
