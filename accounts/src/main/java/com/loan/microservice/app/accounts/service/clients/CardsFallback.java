package com.loan.microservice.app.accounts.service.clients;

import com.loan.microservice.app.accounts.dto.CardsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        log.error("Cards service is unavailable for mobile: {}", mobileNumber);
        return null;
    }
}
