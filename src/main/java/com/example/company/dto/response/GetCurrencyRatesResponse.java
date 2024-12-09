package com.example.company.dto.response;

import java.util.HashMap;
import java.util.Map;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCurrencyRatesResponse {

    @Builder.Default
    Map<String, CodeValue> data = new HashMap<>();

    @Builder
    public record CodeValue(String code, Double value) {}
    ;
}
