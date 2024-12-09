package com.example.company.service;

import java.util.Map;

public interface CurrencyRatesService {

    Map<String, Double> getRatesByCode(String code);
}
