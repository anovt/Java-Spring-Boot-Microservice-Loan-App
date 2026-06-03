package com.loan.microservice.app.accounts.service.clients;

import com.loan.microservice.app.accounts.dto.LoansDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        log.error("Loans service is unavailable for mobile: {}", mobileNumber);
        //LoansDto loansDto = new LoansDto();
        return null;
    }
}
