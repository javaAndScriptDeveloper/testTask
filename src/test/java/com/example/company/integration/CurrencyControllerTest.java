package com.example.company.integration;

import static com.example.company.utils.JsonUtils.OBJECT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.company.dto.request.AddCurrencyRequest;
import com.example.company.dto.response.CurrencyResponse;
import com.example.company.dto.response.GetCurrencyRatesResponse;
import com.example.company.entity.CurrencyEntity;
import com.example.company.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CurrencyControllerTest extends AbstractIntegrationTest {

    @Test
    @SneakyThrows
    public void whenCallGetAllCurrencyCodes_ThenExpectedCurrencyCodesReturned() {
        // given
        var currencyEntities = random.objects(CurrencyEntity.class, 10)
                .peek(it -> it.setId(null))
                .toList();
        currencyRepo.saveAll(currencyEntities);
        var expectedCurrencyCodes =
                currencyEntities.stream().map(CurrencyEntity::getCode).toList();

        // when
        var response = executeGetApiCall(baseApiPath + "/currencies");

        // then
        var responseBody = response.andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var responseDto = OBJECT_MAPPER.readValue(responseBody, new TypeReference<List<String>>() {});

        assertEquals(expectedCurrencyCodes, responseDto);
    }

    @Test
    @SneakyThrows
    public void whenCallAddCurrencyCode_ThenExpectedCurrencyCodeAdded() {
        // given
        var currencyCodeFirst = random.nextObject(String.class);
        var rateFirst = random.nextDouble();

        var currencyCodeSecond = random.nextObject(String.class);
        var rateSecond = random.nextDouble();

        when(currencyRatesApiClient.getCurrencyRates(any(), any()))
                .thenReturn(GetCurrencyRatesResponse.builder()
                        .data(Map.of(
                                currencyCodeFirst,
                                GetCurrencyRatesResponse.CodeValue.builder()
                                        .code(currencyCodeFirst)
                                        .value(rateFirst)
                                        .build(),
                                currencyCodeSecond,
                                GetCurrencyRatesResponse.CodeValue.builder()
                                        .code(currencyCodeSecond)
                                        .value(rateSecond)
                                        .build()))
                        .build());

        var currencyCodeToAdd = "USD";

        var expectedCurrencyEntity = CurrencyEntity.builder()
                .code(currencyCodeToAdd)
                .rates(Map.of(currencyCodeFirst, rateFirst, currencyCodeSecond, rateSecond))
                .build();

        var request = AddCurrencyRequest.builder().code(currencyCodeToAdd).build();

        // when
        var response = executePostApiCall(baseApiPath + "/currencies", request);

        // then
        response.andExpect(status().is(HttpStatus.OK.value()));

        var actualCurrencyEntity = currencyRepo.findAll().stream().findFirst().orElseThrow();

        assertThat(actualCurrencyEntity)
                .usingRecursiveComparison()
                .ignoringFields("id", "modifiedAt")
                .isEqualTo(expectedCurrencyEntity);
    }

    @Test
    @SneakyThrows
    public void whenCallGetCurrencyByCode_ThenExpectedCurrencyReturned() {
        // given
        var expectedCurrencyEntity = random.nextObject(CurrencyEntity.class);
        expectedCurrencyEntity.setId(null);
        expectedCurrencyEntity.setRates(Map.of(random.nextObject(String.class), random.nextObject(Double.class)));
        expectedCurrencyEntity = currencyRepo.save(expectedCurrencyEntity);

        var expectedResponseBody = JsonUtils.toJson(CurrencyResponse.builder()
                .code(expectedCurrencyEntity.getCode())
                .rates(expectedCurrencyEntity.getRates())
                .build());

        // when
        var response =
                executeGetApiCall("%s/currencies/code/%s".formatted(baseApiPath, expectedCurrencyEntity.getCode()));

        // then
        var actualResponseBody =
                response.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(expectedResponseBody, actualResponseBody);
    }
}
