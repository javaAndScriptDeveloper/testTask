package com.example.company.mapper;

import com.example.company.dto.response.CurrencyResponse;
import com.example.company.entity.CurrencyEntity;
import com.example.company.model.Currency;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyMapper {

    CurrencyResponse toDto(Currency model);

    Currency toModel(CurrencyEntity entity);
}
