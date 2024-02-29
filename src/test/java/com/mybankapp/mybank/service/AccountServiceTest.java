package com.mybankapp.mybank.service;

import com.mybankapp.mybank.TestSupport;
import com.mybankapp.mybank.dto.AccountCustomerDto;
import com.mybankapp.mybank.dto.AccountDto;
import com.mybankapp.mybank.dto.converter.AccountDtoConverter;
import com.mybankapp.mybank.dto.requests.CreateAccountRequest;
import com.mybankapp.mybank.dto.requests.UpdateAccountRequest;
import com.mybankapp.mybank.exception.InsufficientBalanceException;
import com.mybankapp.mybank.exception.InvalidAmountException;
import com.mybankapp.mybank.exception.InvalidCurrencyException;
import com.mybankapp.mybank.model.Account;
import com.mybankapp.mybank.model.City;
import com.mybankapp.mybank.model.Currency;
import com.mybankapp.mybank.model.Customer;
import com.mybankapp.mybank.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AccountServiceTest extends TestSupport {

    private AccountService accountService;
    private AccountRepository accountRepository;
    private AccountDtoConverter converter;
    private CustomerService customerService;
    @Before
    public void setUp() throws Exception {
        converter = Mockito.mock(AccountDtoConverter.class);
        customerService = Mockito.mock(CustomerService.class);
        accountRepository = Mockito.mock(AccountRepository.class);

        accountService = new AccountService(accountRepository, customerService, converter);
    }

    @Test
    public void whenCreateMethodCalledWithExistCustomerAndValidCurrency_itShouldReturnValidAccountDto(){
        String id = "id";
        Double balance = 0.0;
        Currency currency = Currency.TRY;
        String requestCurrency = "TRY";
        Customer customer = generateCustomer(id, "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, null, balance, currency);
        AccountCustomerDto customerDto = new AccountCustomerDto(id, "name",
                2000, City.ANKARA, "address");
        CreateAccountRequest request = generateCreateAccountRequest(requestCurrency);
        AccountDto expectedAccountDto = new AccountDto(null, balance, currency, customerDto);
        Mockito.when(customerService.findCustomerById(id)).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(converter.convert(account)).thenReturn(expectedAccountDto);

        AccountDto result = accountService.create(request, id);

        assertEquals(result, expectedAccountDto);
        Mockito.verify(customerService).findCustomerById(id);
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(converter).convert(account);

    }

    @Test(expected = InvalidCurrencyException.class)
    public void whenCreateMethodCalledWithExistCustomerAndInValidCurrency_itShouldThrowInvalidCurrencyException(){
        String id = "id";
        Double balance = 0.0;
        Currency currency = Currency.TRY;
        String requestCurrency = "CND";
        Customer customer = generateCustomer(id, "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, null, balance, currency);
        AccountCustomerDto customerDto = new AccountCustomerDto(id, "name",
                2000, City.ANKARA, "address");
        CreateAccountRequest request = generateCreateAccountRequest(requestCurrency);
        AccountDto expectedAccountDto = new AccountDto(null, balance, currency, customerDto);
        Mockito.when(customerService.findCustomerById(id)).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(converter.convert(account)).thenReturn(expectedAccountDto);

        accountService.create(request, id);

        Mockito.verify(customerService).findCustomerById(id);
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(converter).convert(account);
    }

    @Test
    public void whenGetAllAccountsCalledWithExistingAccounts_itShouldReturnAccountDtoList(){
        Customer customer = generateCustomer("id", "name",
                2000, City.ANKARA, "address");
        Account a1 = generateAccount(customer, "id_1", 0.0, Currency.TRY);
        Account a2 = generateAccount(customer, "id_2", 0.0, Currency.EUR);
        Account a3 = generateAccount(customer, "id_3", 0.0, Currency.USD);

        List<Account> accountList = Arrays.asList(a1, a2, a3);

        Mockito.when(accountRepository.findAll()).thenReturn(accountList);
        List<AccountDto> accountDtoList = accountList.stream()
                .map(converter::convert).collect(Collectors.toList());

        List<AccountDto> expectedAccounDtoList = accountService.getAllAccounts();

        assertEquals(accountDtoList, expectedAccounDtoList);
    }

    @Test
    public void whenGetAllAccountsCalledWithEmptyAccountList_itShouldReturnEmptyList(){
        Mockito.when(accountRepository.findAll()).thenReturn(Collections.emptyList());
        List<AccountDto> expectedAccountDtoList = accountService.getAllAccounts();

        assertEquals(expectedAccountDtoList, Collections.emptyList());
    }

    @Test
    public void whenWithdrawCalledWithValidAmount_itShouldReturnUpdatedAccountDto(){
        Double amount = 500.0;
        Customer customer = generateCustomer("id", "name",
                2000, City.ANKARA, "address");
        AccountCustomerDto customerDto = new AccountCustomerDto("id", "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, "account_id", 1000.0, Currency.USD);
        UpdateAccountRequest request = generateUpdateAccountRequest(amount);
        Mockito.when(accountRepository.findById("account_id")).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        AccountDto accountDto = new AccountDto("account_id", 500.0, Currency.USD, customerDto);
        Mockito.when(converter.convert(account)).thenReturn(accountDto);
        AccountDto result = accountService.withdraw("account_id", request);
        assertEquals(result, accountDto);
    }

    @Test(expected = InvalidAmountException.class)
    public void whenWithdrawCalledWithInvalidAmountThatMinusAmount_itShouldThrowInvalidAmountException(){
        Double amount = -500.0;
        Customer customer = generateCustomer("id", "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, "account_id", 1000.0, Currency.USD);
        UpdateAccountRequest request = generateUpdateAccountRequest(amount);
        Mockito.when(accountRepository.findById("account_id")).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        accountService.withdraw("account_id", request);
    }

    @Test(expected = InsufficientBalanceException.class)
    public void whenWithdrawCalledWithAmountThatMoreThanBalance_itShouldThrowInsufficientBalanceException(){
        Double amount = 5000.0;
        Customer customer = generateCustomer("id", "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, "account_id", 1000.0, Currency.USD);
        UpdateAccountRequest request = generateUpdateAccountRequest(amount);
        Mockito.when(accountRepository.findById("account_id")).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        accountService.withdraw("account_id", request);
    }

    @Test
    public void whenDepositCalledWithValidAmount_itShouldReturnUpdatedAccountDto(){
        Double amount = 500.0;
        Customer customer = generateCustomer("id", "name",
                2000, City.ANKARA, "address");
        AccountCustomerDto customerDto = new AccountCustomerDto("id", "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, "account_id", 1000.0, Currency.USD);
        UpdateAccountRequest request = generateUpdateAccountRequest(amount);
        Mockito.when(accountRepository.findById("account_id")).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        AccountDto accountDto = new AccountDto("account_id", 1500.0, Currency.USD, customerDto);
        Mockito.when(converter.convert(account)).thenReturn(accountDto);
        AccountDto result = accountService.deposit("account_id", request);
        assertEquals(result, accountDto);
    }

    @Test(expected = InvalidAmountException.class)
    public void whenDepositCalledWithInvalidAmountThatMinusAmount_itShouldThrowInvalidAmountException(){
        Double amount = -500.0;
        Customer customer = generateCustomer("id", "name",
                2000, City.ANKARA, "address");
        Account account = generateAccount(customer, "account_id", 1000.0, Currency.USD);
        UpdateAccountRequest request = generateUpdateAccountRequest(amount);
        Mockito.when(accountRepository.findById("account_id")).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        accountService.deposit("account_id", request);

    }




}