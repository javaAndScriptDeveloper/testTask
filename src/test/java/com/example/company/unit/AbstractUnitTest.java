package com.example.company.unit;

import com.example.company.AbstractTest;
import com.example.company.mapper.CurrencyMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AbstractUnitTest extends AbstractTest {

    @Spy
    protected CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);
}
