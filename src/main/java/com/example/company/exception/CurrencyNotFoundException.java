package com.example.company.exception;

import org.springframework.http.HttpStatus;

public class CurrencyNotFoundException extends ApplicationException {

    public CurrencyNotFoundException(String code) {
        super(HttpStatus.NOT_FOUND, "Currency by code '%s' not found".formatted(code));
    }
}
