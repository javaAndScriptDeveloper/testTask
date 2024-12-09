package com.example.company.unit.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.company.entity.CurrencyEntity;
import com.example.company.model.Currency;
import com.example.company.repository.CurrencyRepo;
import com.example.company.service.CurrencyRatesService;
import com.example.company.service.impl.CurrencyServiceImpl;
import com.example.company.unit.AbstractUnitTest;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CurrencyServiceImplTest extends AbstractUnitTest {

    @Mock
    private CurrencyRepo currencyRepo;

    @Mock
    private CurrencyRatesService currencyRatesService;

    @InjectMocks
    private CurrencyServiceImpl sut;

    @Captor
    private ArgumentCaptor<CurrencyEntity> currencyEntityArgumentCaptor;

    @Test
    public void whenCallGetAllCurrencyCodes_ThenExpectedCurrencyCodesReturned() {
        // given
        var expectedCurrencyCodes = random.objects(String.class, 10).collect(Collectors.toSet());
        when(currencyRepo.findAllCurrencyCodes()).thenReturn(expectedCurrencyCodes);

        // when
        var actualCurrencyCodes = sut.getAllCurrencyCodes();

        // then
        assertEquals(expectedCurrencyCodes, actualCurrencyCodes);
        verify(currencyRepo).findAllCurrencyCodes();
    }

    @Test
    public void whenCallGetCurrencyByCode_ThenExpectedCurrencyReturned() {
        // given
        var expectedCurrencyEntity = random.nextObject(CurrencyEntity.class);
        when(currencyRepo.findByCode(any())).thenReturn(Optional.ofNullable(expectedCurrencyEntity));
        var expectedCurrencyModel = Currency.builder()
                .id(expectedCurrencyEntity.getId())
                .rates(expectedCurrencyEntity.getRates())
                .code(expectedCurrencyEntity.getCode())
                .build();
        var currencyCode = random.nextObject(String.class);

        // when
        var actualCurrencyModel = sut.getCurrencyByCode(currencyCode);

        // then
        assertThat(actualCurrencyModel)
                .usingRecursiveComparison()
                .ignoringFields("modifiedAt")
                .isEqualTo(expectedCurrencyModel);
        verify(currencyRepo).findByCode(currencyCode);
    }

    @Test
    public void whenCallAddCurrencyByCode_ThenExpectedCurrencyAdded() {
        // given
        when(currencyRepo.save(any())).thenReturn(random.nextObject(CurrencyEntity.class));
        var currencyRates = Map.of(random.nextObject(String.class), random.nextObject(Double.class));
        when(currencyRatesService.getRatesByCode(any())).thenReturn(currencyRates);
        var currencyCode = random.nextObject(String.class);
        var expectedCurrencyEntity =
                CurrencyEntity.builder().code(currencyCode).rates(currencyRates).build();

        // when
        sut.addCurrencyByCode(currencyCode);

        // then
        verify(currencyRatesService).getRatesByCode(currencyCode);
        verify(currencyRepo).save(currencyEntityArgumentCaptor.capture());

        var actualCurrencyEntity = currencyEntityArgumentCaptor.getValue();

        assertEquals(expectedCurrencyEntity, actualCurrencyEntity);
    }

    @Test
    public void whenCallUpdateCurrencyByCode_ThenExpectedCurrencyUpdated() {
        // given
        var existingCurrencyEntity = random.nextObject(CurrencyEntity.class);
        var updatedRates = Map.of(random.nextObject(String.class), random.nextObject(Double.class));
        var currencyCode = existingCurrencyEntity.getCode();

        when(currencyRepo.findByCode(currencyCode)).thenReturn(Optional.of(existingCurrencyEntity));
        when(currencyRatesService.getRatesByCode(currencyCode)).thenReturn(updatedRates);

        // when
        sut.updateCurrencyByCode(currencyCode);

        // then
        verify(currencyRepo).findByCode(currencyCode);
        verify(currencyRatesService).getRatesByCode(currencyCode);
        verify(currencyRepo).save(currencyEntityArgumentCaptor.capture());

        var actualCurrencyEntity = currencyEntityArgumentCaptor.getValue();

        assertEquals(currencyCode, actualCurrencyEntity.getCode());
        assertEquals(updatedRates, actualCurrencyEntity.getRates());
        assertThat(actualCurrencyEntity)
                .usingRecursiveComparison()
                .ignoringFields("modifiedAt")
                .isEqualTo(existingCurrencyEntity);
    }
}
