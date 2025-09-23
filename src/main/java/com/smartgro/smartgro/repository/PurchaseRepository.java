package com.smartgro.smartgro.repository;

import com.smartgro.smartgro.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = """
    SELECT p.item_id AS itemId,
           SUM(p.quantity) AS totalQty,
           COUNT(*) AS freq,
           AVG(p.price) AS userAvgPrice
    FROM purchases p
    WHERE p.user_id = :userId
      AND p.purchased_at >= (NOW() - INTERVAL 3 MONTH)
    GROUP BY p.item_id
    ORDER BY totalQty DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<Map<String,Object>> topItemsForUserLast3Months(@Param("userId") Long userId, @Param("limit") int limit);

    @Query(value = """
    SELECT r.item_id   AS itemId,
           r.shop_id   AS shopId,
           r.avg_price AS avgPrice
    FROM (
      SELECT item_id, shop_id, AVG(price) AS avg_price
      FROM purchases
      WHERE purchased_at >= (NOW() - INTERVAL 3 MONTH)
      GROUP BY item_id, shop_id
    ) r
    JOIN (
      SELECT item_id, MIN(avg_price) AS min_price
      FROM (
        SELECT item_id, shop_id, AVG(price) AS avg_price
        FROM purchases
        WHERE purchased_at >= (NOW() - INTERVAL 3 MONTH)
        GROUP BY item_id, shop_id
      ) x
      GROUP BY item_id
    ) m ON r.item_id = m.item_id AND r.avg_price = m.min_price
    WHERE r.item_id IN (:itemIds)
    """, nativeQuery = true)
    List<Map<String,Object>> bestShopPerItemLast3Months(@Param("itemIds") List<Long> itemIds);
}
