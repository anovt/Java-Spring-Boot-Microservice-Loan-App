package com.loan.microservice.app.accounts.service.impl;

import com.loan.microservice.app.accounts.dto.AccountsDto;
import com.loan.microservice.app.accounts.dto.CardsDto;
import com.loan.microservice.app.accounts.dto.CustomerDetailsDto;
import com.loan.microservice.app.accounts.dto.LoansDto;
import com.loan.microservice.app.accounts.entity.Accounts;
import com.loan.microservice.app.accounts.entity.Customer;
import com.loan.microservice.app.accounts.exceptions.ResourceNotFoundException;
import com.loan.microservice.app.accounts.mapper.AccountsMapper;
import com.loan.microservice.app.accounts.mapper.CustomerMapper;
import com.loan.microservice.app.accounts.repository.AccountsRepository;
import com.loan.microservice.app.accounts.repository.CustomerRepository;
import com.loan.microservice.app.accounts.service.ICustomerService;
import com.loan.microservice.app.accounts.service.clients.CardsFeignClient;
import com.loan.microservice.app.accounts.service.clients.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;



    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Accounts","customerId",String.valueOf(customer.getCustomerId())));


        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansResponse = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        if (loansResponse != null && loansResponse.getStatusCode().is2xxSuccessful()) {
            customerDetailsDto.setLoansDto(loansResponse.getBody());
        }

        ResponseEntity<CardsDto> cardResponse = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        if (cardResponse != null && cardResponse.getStatusCode().is2xxSuccessful()) {
            customerDetailsDto.setCardsDto(cardResponse.getBody());
        }

        return customerDetailsDto;
    }
}
