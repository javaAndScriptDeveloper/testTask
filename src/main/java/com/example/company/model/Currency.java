package com.example.company.model;

import java.time.Instant;
import java.util.Map;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency {

    Long id;

    Map<String, Double> rates;

    String code;

    Instant modifiedAt;
}
