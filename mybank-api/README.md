# Mybank Spring Boot Application
___

## Summary
___
This project provides to CRUD operations for customer and accounts that depends on existing customer.

## Customer API Endpoints
___
1. GET "/v1/customer": is base endpoint and it returns List of CustomerDto
2. GET "/v1/customer/{id}": is returns specified CustomerDto, if Customer exist.
3. POST "/v1/customer/create": is required request that valid Customer, then request saves to DB and it returns CustomerDto.
4. PUT "/v1/customer/update/{id}": is update valid customer and it returns updated CustomerDto.
5. DELETE "/v1/customer/delete/{id}": is delete customer if exist, then returns Response Status 200 OK.

## Account API Endpoints
___
1. GET "/v1/account/": is base endpoint and it returns List of AccountDto
2. GET "/v1/account/{id}": is returns AccountDto, if Account is exist that searched id.
3. POST "/v1/account/create": is required valid Account request and exist customer id, then request saves to DB and it returns AccountDto.
4. DELETE "/v1/account/delete": is delete account if exist, then returns Response Status 200 OK.
5. PUT "/v1/account/deposit": is required exist account id and valid account request, then it returns updated AccountDto.
6. PUT "/v1/account/withdraw": is required exist account id and valid account request, then it returns updated AccountDto.

## Prerequisites
___
* Maven
* Docker
