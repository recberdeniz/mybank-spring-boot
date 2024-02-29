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

import com.mybankapp.mybank.dto.AccountDto;
import com.mybankapp.mybank.dto.requests.CreateAccountRequest;
import com.mybankapp.mybank.dto.requests.UpdateAccountRequest;
import com.mybankapp.mybank.service.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

	private final AccountService accountService;
	
	
	public AccountController(AccountService accountService) {

		this.accountService = accountService;
	}


	@PostMapping("/create/{id}")
	public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest accountRequest,
			@PathVariable String id){
		return ResponseEntity.ok(accountService.create(accountRequest, id));
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){

		return ResponseEntity.ok(accountService.getAllAccounts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountWithId(@PathVariable String id){
		return ResponseEntity.ok(accountService.getAccountWithId(id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable String id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/withdraw/{id}")
	public ResponseEntity<AccountDto> withdraw(@PathVariable String id,
			@Valid @RequestBody UpdateAccountRequest accountRequest){
		return ResponseEntity.ok(accountService.withdraw(id, accountRequest));
	}
	
	@PutMapping("/deposit/{id}")
	public ResponseEntity<AccountDto> deposit(@PathVariable String id,
			@Valid @RequestBody UpdateAccountRequest accountRequest){
		return ResponseEntity.ok(accountService.deposit(id, accountRequest));
	}
}
