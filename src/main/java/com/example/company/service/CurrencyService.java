package com.example.company.service;

import com.example.company.model.Currency;
import java.util.Set;

public interface CurrencyService {

    Set<String> getAllCurrencyCodes();

    Currency getCurrencyByCode(String code);

    void addCurrencyByCode(String code);

    void updateCurrencyByCode(String code);
}
