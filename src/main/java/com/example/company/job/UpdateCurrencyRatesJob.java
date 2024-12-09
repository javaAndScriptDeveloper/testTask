package com.example.company.job;

import com.example.company.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCurrencyRatesJob {

    private final CurrencyService currencyService;

    @Scheduled(
            initialDelayString = "${job.update-currency-rates.initial-delay}",
            fixedDelayString = "${job.update-currency-rates.fixed-delay}")
    public void updateCurrencyRates() {
        var currencyCodes = currencyService.getAllCurrencyCodes();
        log.debug("Fetched {} currencies to update rates", currencyCodes.size());
        currencyCodes.forEach(currencyService::updateCurrencyByCode);
        log.debug("Updated rates for {} currencies", currencyCodes.size());
    }
}
