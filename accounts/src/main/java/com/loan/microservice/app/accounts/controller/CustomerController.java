package com.loan.microservice.app.accounts.controller;

import com.loan.microservice.app.accounts.dto.CustomerDetailsDto;
import com.loan.microservice.app.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "APIs for Customer Details in Loans Application",
        description = "APIs to Get Customer Details in Loans App"
)
@RestController
@RequestMapping(path = "/api/v1")
@Validated
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final ICustomerService customerService;

    @GetMapping(value = "/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader(name = "eazybank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "^[0-9]{10}$",
            message = "Mobile number should be 10 digits") String mobileNumber) {
        // Implementation to fetch and return all customer details
        logger.debug("eazybank-correlation-id found: {}", correlationId);
        CustomerDetailsDto customerDetails = customerService.fetchCustomerDetails(mobileNumber,correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
    }
}
