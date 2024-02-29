package com.mybankapp.mybank;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.CustomerRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MybankApplication implements CommandLineRunner{

	private final CustomerRepository customerRepository;
	
	public MybankApplication(CustomerRepository customerRepository) {

		this.customerRepository = customerRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(MybankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Customer c1 = customerRepository
				.save(new Customer(null,
						"John",
						1989,
						City.ANKARA,
						"Ev",
						new ArrayList<>()));
		Customer c2 = customerRepository
				.save(new Customer(null,
						"Ginger",
						1995,
						City.ISTANBUL,
						"Ev",
						new ArrayList<>()));
		Customer c3 = customerRepository
				.save(new Customer(null,
						"Susan",
						1999,
						City.IZMIR,
						"Ev",
						new ArrayList<>()));
		System.out.println(c1);
		
	}

}
