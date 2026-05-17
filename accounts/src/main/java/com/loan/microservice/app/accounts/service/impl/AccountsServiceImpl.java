package com.loan.microservice.app.accounts.service.impl;

import com.loan.microservice.app.accounts.constants.AccountConstants;
import com.loan.microservice.app.accounts.dto.AccountsDto;
import com.loan.microservice.app.accounts.dto.CustomerDto;
import com.loan.microservice.app.accounts.entity.Accounts;
import com.loan.microservice.app.accounts.entity.Customer;
import com.loan.microservice.app.accounts.exceptions.CustomerExistEcxeption;
import com.loan.microservice.app.accounts.exceptions.ResourceNotFoundException;
import com.loan.microservice.app.accounts.mapper.AccountsMapper;
import com.loan.microservice.app.accounts.mapper.CustomerMapper;
import com.loan.microservice.app.accounts.repository.AccountsRepository;
import com.loan.microservice.app.accounts.repository.CustomerRepository;
import com.loan.microservice.app.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerDto - CustomerDto Object
     */

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerExistEcxeption("Customer with mobile number " + customer.getMobileNumber() + " already exists.");
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(newAccount(savedCustomer));

    }



    private Accounts newAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 100000000L + new Random().nextInt(9000000);
        newAccounts.setAccountNumber(randomAccountNumber);
        newAccounts.setAccountType(AccountConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountConstants.ADDRESS);


        return newAccounts;
    }

    /**
     * This method is responsible for retrieving the account details of a customer based on their mobile number. It takes a mobile number as input and returns a CustomerDto object containing the account details of the customer associated with that mobile number. If no customer is found with the provided mobile number, it may throw an exception or return null, depending on the implementation.
     * @param mobileNumber
     * @return CustomerDto
     */
    @Override
    public CustomerDto getAccountDetails(String mobileNumber) {
        Optional<Customer> existingCustomer = customerRepository.findUserDetailsByMobileNumber(mobileNumber);
        if (existingCustomer.isPresent()) {
            throw new CustomerExistEcxeption("Customer with mobile number " + mobileNumber + " already exists.");
        }


        return CustomerMapper.mapToCustomerDto(existingCustomer.get(),new CustomerDto());
    }

    @Override
    public CustomerDto getAccountDetailsByMobileNumber(String mobileNumber) {

        Customer existingCustomer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(existingCustomer.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Accounts","customerId",String.valueOf(existingCustomer.getCustomerId())));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(existingCustomer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        return customerDto;
    }

    /**
     *
     * @param customerDto
     * @return Boolean
     */

    @Override
    public Boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * This method is responsible for deleting an account based on the provided mobile number. It takes a mobile number as input and performs the necessary operations to delete the account associated with that mobile number. The method may return a boolean value indicating whether the deletion was successful or not, depending on the implementation.
     * @param mobileNumber
     * @return
     */

    @Override
    public Boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }


}
