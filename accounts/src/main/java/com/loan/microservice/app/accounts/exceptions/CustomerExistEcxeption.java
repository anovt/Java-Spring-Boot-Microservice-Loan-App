package com.loan.microservice.app.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerExistEcxeption extends  RuntimeException{

    public CustomerExistEcxeption(String message) {
        super(message);
    }

}
