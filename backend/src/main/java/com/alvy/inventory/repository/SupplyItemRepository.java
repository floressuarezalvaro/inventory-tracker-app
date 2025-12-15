package com.alvy.inventory.repository;

import com.alvy.inventory.entity.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
    List<SupplyItem> findByCategory(String category);

    List<SupplyItem> findByQuantityLessThan(int threshold);

    List<SupplyItem> findByNameContainingIgnoreCase(String name);

    List<SupplyItem> findByQuantityOnHoldGreaterThan(int threshold);
}
