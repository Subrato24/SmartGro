package com.smartgro.smartgro.repository;

import com.smartgro.smartgro.entity.ShoppingDateSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingDateSummaryRepository extends JpaRepository<ShoppingDateSummary, Long> {
    Optional<ShoppingDateSummary> findTopByUserIdOrderByTotalAmountAsc(Long userId);
}
