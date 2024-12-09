package com.example.company.service.impl;

import com.example.company.entity.CurrencyEntity;
import com.example.company.exception.CurrencyNotFoundException;
import com.example.company.mapper.CurrencyMapper;
import com.example.company.model.Currency;
import com.example.company.repository.CurrencyRepo;
import com.example.company.service.CurrencyRatesService;
import com.example.company.service.CurrencyService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyMapper currencyMapper;

    private final CurrencyRepo currencyRepo;

    private final CurrencyRatesService currencyRatesService;

    @Override
    public Set<String> getAllCurrencyCodes() {
        return currencyRepo.findAllCurrencyCodes();
    }

    @Override
    @Cacheable("currencyByCode")
    public Currency getCurrencyByCode(String code) {
        return currencyRepo
                .findByCode(code)
                .map(currencyMapper::toModel)
                .orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    @Override
    public void addCurrencyByCode(String code) {
        var rates = currencyRatesService.getRatesByCode(code);
        currencyRepo.save(CurrencyEntity.builder().code(code).rates(rates).build());
    }

    @Override
    @Transactional
    public void updateCurrencyByCode(String code) {
        var currencyEntity = currencyRepo.findByCode(code).orElseThrow(() -> new CurrencyNotFoundException(code));
        var rates = currencyRatesService.getRatesByCode(code);
        currencyEntity.setRates(rates);
        currencyRepo.save(currencyEntity);
    }
}
