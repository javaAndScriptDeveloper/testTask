package com.example.company.repository;

import com.example.company.entity.CurrencyEntity;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrencyRepo extends JpaRepository<CurrencyEntity, Long> {

    @Query("SELECT c.code FROM CurrencyEntity c")
    Set<String> findAllCurrencyCodes();

    Optional<CurrencyEntity> findByCode(String code);
}
