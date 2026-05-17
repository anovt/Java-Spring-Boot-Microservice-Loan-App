package com.loan.microservice.app.accounts.service;

import com.loan.microservice.app.accounts.dto.CustomerDetailsDto;
import org.springframework.stereotype.Service;


public interface ICustomerService {

    /**
     * Fetches the customer details based on the provided mobile number.
      * @param mobileNumber
     * @return CustomerDetailsDto
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
