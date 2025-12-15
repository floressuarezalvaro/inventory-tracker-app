package com.alvy.inventory.repository;

import com.alvy.inventory.entity.RequestItem;
import com.alvy.inventory.entity.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestItemRepository extends JpaRepository<RequestItem, Long> {
    List<RequestItem> findByOnHold(boolean onHold);

    List<RequestItem> findByFulfilled(boolean fulfilled);

    List<RequestItem> findByItem(SupplyItem item);

    List<RequestItem> findByItemAndOnHold(SupplyItem item, boolean onHold);
}
