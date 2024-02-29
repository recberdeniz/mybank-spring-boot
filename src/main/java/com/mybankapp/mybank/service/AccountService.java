package com.mybankapp.mybank.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mybankapp.mybank.exception.InvalidAmountException;
import org.springframework.stereotype.Service;

import com.mybankapp.mybank.dto.AccountDto;
import com.mybankapp.mybank.dto.converter.AccountDtoConverter;
import com.mybankapp.mybank.dto.requests.CreateAccountRequest;
import com.mybankapp.mybank.dto.requests.UpdateAccountRequest;
import com.mybankapp.mybank.exception.AccountNotFoundException;
import com.mybankapp.mybank.exception.InsufficientBalanceException;
import com.mybankapp.mybank.exception.InvalidCurrencyException;
import com.mybankapp.mybank.model.Account;
import com.mybankapp.mybank.model.Currency;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.AccountRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final CustomerService customerService;
	private final AccountDtoConverter converter;
	
	public AccountService(AccountRepository accountRepository, 
			CustomerService customerService,
			AccountDtoConverter converter) {
		this.accountRepository = accountRepository;
		this.customerService = customerService;
		this.converter = converter;
	}

	public AccountDto create(CreateAccountRequest accountRequest, String id) {
		Customer customer = customerService.findCustomerById(id);
		Currency currency = checkValidCurrency(accountRequest.getCurrency());
		Account account = new Account(null, 0.0, currency, customer);
		return converter.convert(accountRepository.save(account));
	}

	private Currency checkValidCurrency(String currency) {
		Currency control = Arrays.stream(Currency.values())
				.filter(c -> c.name().equals(currency))
				.findFirst()
				.orElseThrow(() -> new InvalidCurrencyException("Invalid currency: " + currency));
		return control;
	}

	public List<AccountDto> getAllAccounts() {
		
		return accountRepository.findAll()
				.stream()
				.map(converter::convert).collect(Collectors.toList());
	}
	

	public AccountDto getAccountWithId(String id) {
		
		return converter
				.convert(findAccountById(id));
	}
	
	private Account findAccountById(String id) {
		return accountRepository
				.findById(id).
				orElseThrow(
						() -> new AccountNotFoundException("Account could not find with id: " + id));
	}

	public void deleteAccount(String id) {
		
		Account account = findAccountById(id);
		
		accountRepository.delete(account);
		
	}

	public AccountDto withdraw(String id, UpdateAccountRequest accountRequest)
			throws InsufficientBalanceException{
		Account account = findAccountById(id);
		if(accountRequest.getAmount() < 0){
			throw new InvalidAmountException("Invalid amount: " + accountRequest.getAmount());
		}
		if(accountRequest.getAmount() > account.getBalance()) {
			throw new InsufficientBalanceException("Insufficient balance: " +
		account.getBalance() + " Amount: " + accountRequest.getAmount());
		}
		account.setBalance(account.getBalance() - accountRequest.getAmount());
		return converter.convert(accountRepository.save(account));
	}

	public AccountDto deposit(String id, UpdateAccountRequest accountRequest) {
		Account account = findAccountById(id);
		if(accountRequest.getAmount() < 0){
			throw new InvalidAmountException("Invalid amount: " + accountRequest.getAmount());
		}
		account.setBalance(accountRequest.getAmount() + account.getBalance());

		return converter.convert(accountRepository.save(account));
	}
	
	
	
	
}
