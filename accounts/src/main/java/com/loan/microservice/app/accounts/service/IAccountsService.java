package com.loan.microservice.app.accounts.service;

import com.loan.microservice.app.accounts.dto.CustomerDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface IAccountsService {

    /**
     * This method is responsible for creating a new account for a customer. It takes a CustomerDto object as input, which contains the necessary information about the customer, such as name, email, and mobile number. The method will handle the logic to create an account based on the provided customer details.
     * @param customerDto - CustomerDto Object
     */
    public void createAccount(CustomerDto customerDto);

    /**
     * This method is responsible for retrieving the account details of a customer based on their mobile number. It takes a mobile number as input and returns a CustomerDto object containing the account details of the customer associated with that mobile number. If no customer is found with the provided mobile number, it may throw an exception or return null, depending on the implementation.
     * @param mobileNumber
     * @return
     */
    public CustomerDto getAccountDetails(String mobileNumber);

    /**
     * This method is responsible for getting account details by mobile number. It takes a mobile number as input and returns a CustomerDto object containing the account details of the customer associated with that mobile number. If no customer is found with the provided mobile number, it may throw an exception or return null, depending on the implementation.
     * @param mobileNumber
     * @return
     */
    public CustomerDto getAccountDetailsByMobileNumber(String mobileNumber);


    public Boolean updateAccount(CustomerDto customerDto);


    /**
     * This method is responsible for deleting an account based on the provided mobile number. It takes a mobile number as input and performs the necessary operations to delete the account associated with that mobile number. The method may return a boolean value indicating whether the deletion was successful or not, depending on the implementation.
     * @param mobileNumber
     * @return
     */
    public Boolean deleteAccount(String mobileNumber);
}
