package com.alvy.inventory.service;

import com.alvy.inventory.entity.SupplyItem;
import com.alvy.inventory.repository.SupplyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyItemService {

    private final SupplyItemRepository repository;

    public List<SupplyItem> getAllItems() {
        return repository.findAll();
    }

    public SupplyItem getItemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply item not found with id: " + id));
    }

    @Transactional
    public SupplyItem createItem(SupplyItem item) {
        return repository.save(item);
    }

    @Transactional
    public SupplyItem updateItem(Long id, SupplyItem updatedItem) {
        SupplyItem existingItem = getItemById(id);

        existingItem.setName(updatedItem.getName());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setCategory(updatedItem.getCategory());

        return repository.save(existingItem);
    }

    @Transactional
    public void deleteItem(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Supply item not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public List<SupplyItem> getItemsByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<SupplyItem> getLowStockItems(int threshold) {
        return repository.findByQuantityLessThan(threshold);
    }

    public List<SupplyItem> searchItemsByName(String searchTerm) {
        return repository.findByNameContainingIgnoreCase(searchTerm);
    }

    public List<SupplyItem> getItemsWithHolds() {
        return repository.findByQuantityOnHoldGreaterThan(0);
    }
    
    @Transactional
    public SupplyItem addQuantity(Long id, int amount) {
        SupplyItem item = getItemById(id);
        item.updateQuantity(amount);
        return repository.save(item);
    }
}
