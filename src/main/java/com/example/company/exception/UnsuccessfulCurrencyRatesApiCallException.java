package com.example.company.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;

public class UnsuccessfulCurrencyRatesApiCallException extends ApplicationException {

    public UnsuccessfulCurrencyRatesApiCallException(FeignException exception) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to call currency rates provider API", exception);
    }
}
