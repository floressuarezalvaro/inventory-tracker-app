package com.alvy.inventory.controller;

import com.alvy.inventory.entity.SupplyItem;
import com.alvy.inventory.service.SupplyItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class SupplyItemController {

    private final SupplyItemService service;

    @GetMapping
    public ResponseEntity<List<SupplyItem>> getAllItems() {
        return ResponseEntity.ok(service.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<SupplyItem> createItem(@RequestBody SupplyItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createItem(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplyItem> updateItem(@PathVariable Long id, @RequestBody SupplyItem item) {
        return ResponseEntity.ok(service.updateItem(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<SupplyItem>> getLowStockItems(@RequestParam(defaultValue = "10") int threshold) {
        return ResponseEntity.ok(service.getLowStockItems(threshold));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SupplyItem>> searchItems(@RequestParam String name) {
        return ResponseEntity.ok(service.searchItemsByName(name));
    }

    @GetMapping("/on-hold")
    public ResponseEntity<List<SupplyItem>> getItemsWithHolds() {
        return ResponseEntity.ok(service.getItemsWithHolds());
    }

    @PostMapping("/{id}/replenish")
    public ResponseEntity<SupplyItem> addQuantity(@PathVariable Long id, @RequestBody QuantityRequest request) {
        return ResponseEntity.ok(service.addQuantity(id, request.getAmount()));
    }

    public static class QuantityRequest {
        private int amount;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
