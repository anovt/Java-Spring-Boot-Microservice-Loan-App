package com.loan.microservice.app.accounts.mapper;

import com.loan.microservice.app.accounts.dto.CustomerDetailsDto;
import com.loan.microservice.app.accounts.dto.CustomerDto;
import com.loan.microservice.app.accounts.entity.Customer;

import java.util.Optional;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer,CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }


    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer,CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

}