package com.example.company.client;

import com.example.company.dto.response.GetCurrencyRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currencyRatesApi", url = "${api.currency-rates.url}")
public interface CurrencyRatesApiClient {

    @GetMapping("/latest")
    GetCurrencyRatesResponse getCurrencyRates(
            @RequestParam(name = "apikey") String apiKey,
            @RequestParam(name = "base_currency") String baseCurrencyCode);
}
