package com.example.company.service.impl;

import com.example.company.client.CurrencyRatesApiClient;
import com.example.company.dto.response.GetCurrencyRatesResponse;
import com.example.company.exception.UnsuccessfulCurrencyRatesApiCallException;
import com.example.company.service.CurrencyRatesService;
import feign.FeignException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private final CurrencyRatesApiClient currencyRatesApiClient;

    @Value("${api.currency-rates.api-key}")
    private final String apiKey;

    @Override
    public Map<String, Double> getRatesByCode(String currencyCode) {

        GetCurrencyRatesResponse response;
        try {
            response = currencyRatesApiClient.getCurrencyRates(apiKey, currencyCode);
        } catch (FeignException feignException) {
            throw new UnsuccessfulCurrencyRatesApiCallException(feignException);
        }

        return response.getData().values().stream()
                .collect(Collectors.toMap(
                        GetCurrencyRatesResponse.CodeValue::code, GetCurrencyRatesResponse.CodeValue::value));
    }
}
