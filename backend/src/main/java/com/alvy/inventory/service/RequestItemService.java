package com.alvy.inventory.service;

import com.alvy.inventory.entity.RequestItem;
import com.alvy.inventory.entity.SupplyItem;
import com.alvy.inventory.repository.RequestItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestItemService {

    private final RequestItemRepository repository;

    public List<RequestItem> getAllRequestItems() {
        return repository.findAll();
    }

    public RequestItem getRequestItemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request item not found with id: " + id));
    }

    public List<RequestItem> getItemsOnHold() {
        return repository.findByOnHold(true);
    }

    public List<RequestItem> getFulfilledItems() {
        return repository.findByFulfilled(true);
    }

    public List<RequestItem> getRequestItemsBySupplyItem(SupplyItem item) {
        return repository.findByItem(item);
    }
    
    public List<RequestItem> getItemsOnHoldForSupplyItem(SupplyItem item) {
        return repository.findByItemAndOnHold(item, true);
    }
}
