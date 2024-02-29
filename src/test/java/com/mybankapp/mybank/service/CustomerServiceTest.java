package com.mybankapp.mybank.service;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import com.mybankapp.mybank.TestSupport;
import com.mybankapp.mybank.dto.CustomerDto;
import com.mybankapp.mybank.dto.requests.CreateCustomerRequest;
import com.mybankapp.mybank.dto.requests.UpdateCustomerRequest;
import com.mybankapp.mybank.exception.InvalidCityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mybankapp.mybank.dto.converter.CustomerDtoConverter;
import com.mybankapp.mybank.exception.CustomerNotFoundException;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.CustomerRepository;


public class CustomerServiceTest extends TestSupport {

	private CustomerService service;
	private CustomerRepository customerRepository;
	private CustomerDtoConverter converter;



	@Before
	public void setUp() throws Exception {
		
		customerRepository = Mockito.mock(CustomerRepository.class);
		converter = Mockito.mock(CustomerDtoConverter.class);
		service = new CustomerService(customerRepository, converter);
	}
	
	@Test
	public void whenGetByCustomerId_WhenCustomerIdExist_shouldReturnCustomer() {
		Customer customer = generateCustomer("123", "name", 2000,
				City.ANKARA, "address");
		Mockito.when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
		CustomerDto result = service.getCustomerWithId("123");
		CustomerDto expectedCustomerDto = converter.convert(customer);
		assertEquals(expectedCustomerDto, result);
		Mockito.verify(customerRepository).findById("123");
	}
	
	@Test
	public void whenFindByCustomerId_WhenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {
		
		Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());
		
		assertThrows(CustomerNotFoundException.class, () -> service.findCustomerById("id"));
		Mockito.verify(customerRepository).findById("id");
	}

	@Test
	public void whenCreateCustomerCalledWithValidCustomerRequest_itShouldReturnValidCustomerDto(){
		CreateCustomerRequest customerRequest = generateCreateCustomerRequest("name",
				2000, "ANKARA", "address");
		Customer expectedCustomer = generateCustomer("123",
				"name", 2000, City.ANKARA, "address");
		Mockito.when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

		CustomerDto actualDto = service.createCustomer(customerRequest);

		assertEquals(actualDto, converter.convert(expectedCustomer));
		Mockito
				.verify(customerRepository,Mockito.times(1))
				.save(Mockito.any(Customer.class));
		Mockito
				.verify(converter, Mockito.times(1))
				.convert(Mockito.any(Customer.class));

	}

	@Test(expected = InvalidCityException.class)
	public void whenCreateCustomerCalledWithInvalidCustomerRequest_itShouldThrowInvalidCityException(){
		CreateCustomerRequest customerRequest = generateCreateCustomerRequest("name",
				2000, "InvalidCity", "address");
		service.createCustomer(customerRequest);
	}

	@Test
	public void whenGetAllCustomersCalled_ifThereIsNoRegisteredCustomer_itShouldReturnEmptyList(){
		Mockito.when(customerRepository.findAll()).thenReturn(Collections.emptyList());
		List<CustomerDto> actualDtoList = service.getAllCustomers();
		assertEquals(actualDtoList, Collections.emptyList());

	}

	@Test
	public void whenGetAllCustomersCalled_ifExistsCustomerList_itShouldReturnCustomerDtoList(){
		List<Customer> customerList = Arrays.asList(
			generateCustomer("111", "name1", 2000, City.ANKARA, "address1"),
			generateCustomer("222", "name2", 2000, City.ISTANBUL, "address2")
		);
		Mockito.when(customerRepository.findAll()).thenReturn(customerList);
		List<CustomerDto> expectedDtoList = customerList.stream()
				.map(converter::convert)
				.collect(Collectors.toList());

		List<CustomerDto> actualDtoList = service.getAllCustomers();
		assertEquals(actualDtoList, expectedDtoList);
	}

	@Test
	public void whenUpdateCustomerCalledWithValidUpdateCustomerRequest_itShouldReturnValidCustomerDto(){
		String id = "123";
		String updatedCity = "ISTANBUL";
		String updatedAddress = "new address";
		UpdateCustomerRequest request = generateUpdateCustomerRequest(updatedCity, updatedAddress);

		Customer customer = new Customer(id, "name",
				2000, City.ANKARA, "address", null);

		Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		CustomerDto actualDto = new CustomerDto(id,
				"name", 2000, City.ISTANBUL, updatedAddress, null);
		Mockito.when(converter.convert(customer)).thenReturn(actualDto);
		CustomerDto expectedDto = service.updateCustomer(request, id);
		assertEquals(actualDto, expectedDto);
		Mockito.verify(customerRepository).findById(id);
		Mockito.verify(customerRepository).save(customer);
		Mockito.verify(converter).convert(customer);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void whenUpdateCustomerCalledWithNotExistCustomerId_itShouldThrowCustomerNotFoundException(){
		String updatedCity = "ISTANBUL";
		String updatedAddress = "updatedAddress";
		String id = "id";
		UpdateCustomerRequest customerRequest = new UpdateCustomerRequest();
		customerRequest.setCity(updatedCity);
		customerRequest.setAddress(updatedAddress);
		Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());
		service.updateCustomer(customerRequest, id);
	}



}
