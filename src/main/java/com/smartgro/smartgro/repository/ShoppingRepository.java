package com.smartgro.smartgro.repository;

import com.smartgro.smartgro.dto.ShoppingResponseDto;
import com.smartgro.smartgro.entity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingRepository extends JpaRepository<Shopping, Long> {

    // Directly return DTO to avoid LazyInitializationException
    @Query("SELECT new com.smartgro.smartgro.dto.ShoppingResponseDto(" +
            "s.id, u.name, sh.name, i.name, s.quantity, s.price, s.total, s.createdAt) " +
            "FROM Shopping s " +
            "JOIN s.user u " +
            "JOIN s.shop sh " +
            "JOIN s.item i " +
            "WHERE u.id = :userId")
    List<ShoppingResponseDto> findByUserId(@Param("userId") Long userId);

    @Query("SELECT DATE(s.createdAt), SUM(s.total) " +
            "FROM Shopping s " +
            "WHERE s.user.id = :userId " +
            "GROUP BY DATE(s.createdAt)")
    List<Object[]> getSpendingByDate(@Param("userId") Long userId);

    @Query("SELECT s.shop.name, SUM(s.total) " +
            "FROM Shopping s " +
            "WHERE s.user.id = :userId " +
            "GROUP BY s.shop.name")
    List<Object[]> getSpendingByShop(@Param("userId") Long userId);
}
