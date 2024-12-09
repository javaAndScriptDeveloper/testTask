package com.example.company.unit.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.company.client.CurrencyRatesApiClient;
import com.example.company.dto.response.GetCurrencyRatesResponse;
import com.example.company.exception.UnsuccessfulCurrencyRatesApiCallException;
import com.example.company.service.impl.CurrencyRatesServiceImpl;
import com.example.company.unit.AbstractUnitTest;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CurrencyRatesServiceImplTest extends AbstractUnitTest {

    private final String apiKey = random.nextObject(String.class);

    private final CurrencyRatesApiClient currencyRatesApiClient = Mockito.mock(CurrencyRatesApiClient.class);

    private final CurrencyRatesServiceImpl sut = new CurrencyRatesServiceImpl(currencyRatesApiClient, apiKey);

    @Test
    public void whenCallGetRatesByCode_WithSuccessfulResponse_ThenExpectedRatesReturned() {
        // given
        var currencyCode = random.nextObject(String.class);
        var firstCodeValue = GetCurrencyRatesResponse.CodeValue.builder()
                .code(random.nextObject(String.class))
                .value(random.nextObject(Double.class))
                .build();
        var secondCodeValue = GetCurrencyRatesResponse.CodeValue.builder()
                .code(random.nextObject(String.class))
                .value(random.nextObject(Double.class))
                .build();
        var successfulResponse = GetCurrencyRatesResponse.builder()
                .data(Map.of(
                        firstCodeValue.code(),
                        GetCurrencyRatesResponse.CodeValue.builder()
                                .code(firstCodeValue.code())
                                .value(firstCodeValue.value())
                                .build(),
                        secondCodeValue.code(),
                        GetCurrencyRatesResponse.CodeValue.builder()
                                .code(secondCodeValue.code())
                                .value(secondCodeValue.value())
                                .build()))
                .build();

        var expectedRates =
                Map.of(firstCodeValue.code(), firstCodeValue.value(), secondCodeValue.code(), secondCodeValue.value());

        when(currencyRatesApiClient.getCurrencyRates(any(), any())).thenReturn(successfulResponse);

        // when
        var actualRates = sut.getRatesByCode(currencyCode);

        // then
        assertThat(actualRates).isEqualTo(expectedRates);
        verify(currencyRatesApiClient).getCurrencyRates(apiKey, currencyCode);
    }

    @Test
    public void whenCallGetRatesByCode_WithUnsuccessfulResponse_ThenThrowsException() {
        // given
        var currencyCode = random.nextObject(String.class);

        var request = Request.create(
                Request.HttpMethod.GET,
                "https://example.com/api/resource",
                Collections.emptyMap(),
                null,
                new RequestTemplate());
        var response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(request)
                .body("Resource not found".getBytes(StandardCharsets.UTF_8))
                .headers(Collections.emptyMap())
                .build();
        var exception = FeignException.errorStatus("GET /api/resource", response);

        when(currencyRatesApiClient.getCurrencyRates(any(), any())).thenThrow(exception);

        // expected
        assertThrows(UnsuccessfulCurrencyRatesApiCallException.class, () -> sut.getRatesByCode(currencyCode));

        verify(currencyRatesApiClient).getCurrencyRates(apiKey, currencyCode);
    }
}
