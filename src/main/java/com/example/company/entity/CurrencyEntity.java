package com.example.company.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Map;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currencies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    Map<String, Double> rates;

    @Column(unique = true, nullable = false)
    String code;

    @UpdateTimestamp
    @Column(nullable = false)
    Instant modifiedAt;
}
