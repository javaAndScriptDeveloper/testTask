package com.example.company.controller;

import com.example.company.dto.request.AddCurrencyRequest;
import com.example.company.dto.response.CurrencyResponse;
import com.example.company.mapper.CurrencyMapper;
import com.example.company.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.path}/currencies")
@Tag(name = "Currencies", description = "Currencies endpoints")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    @GetMapping
    @Operation(summary = "Get all currency codes", description = "Retrieves a list of all available currency codes.")
    public Set<String> getAllCurrencyCodes() {
        return currencyService.getAllCurrencyCodes();
    }

    @GetMapping("/code/{code}")
    @Operation(
            summary = "Get currency by code",
            description = "Retrieves details of a specific currency based on the provided currency code.")
    public CurrencyResponse getCurrencyByCode(
            @Parameter(description = "The currency code to fetch the currency details for", required = true)
                    @PathVariable
                    String code) {
        var currency = currencyService.getCurrencyByCode(code);
        return currencyMapper.toDto(currency);
    }

    @PostMapping
    @Operation(
            summary = "Add a currency by code",
            description = "Adds a new currency based on the provided currency code.")
    public void addCurrencyByCode(
            @Parameter(description = "The currency request body to add a new currency", required = true)
                    @RequestBody
                    @Valid
                    AddCurrencyRequest request) {
        currencyService.addCurrencyByCode(request.getCode());
    }
}
