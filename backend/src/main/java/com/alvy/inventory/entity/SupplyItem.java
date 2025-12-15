package com.alvy.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supply_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int quantityOnHold = 0;

    @Column(nullable = false)
    private String category;

    public int getQuantityAvailable() {
        return quantity - quantityOnHold;
    }

    public void holdQuantity(int amount) {
        if (amount > getQuantityAvailable()) {
            throw new IllegalStateException("Insufficient available quantity. Available: " + getQuantityAvailable() + ", Requested: " + amount);
        }
        this.quantityOnHold += amount;
    }

    public void releaseHold(int amount) {
        if (amount > quantityOnHold) {
            throw new IllegalStateException("Cannot release more than currently on hold");
        }
        this.quantityOnHold -= amount;
    }

    public void fulfillHold(int amount) {
        if (amount > quantityOnHold) {
            throw new IllegalStateException("Cannot fulfill more than currently on hold");
        }
        this.quantity -= amount;
        this.quantityOnHold -= amount;
    }

    public void updateQuantity(int amount) {
        this.quantity += amount;
    }
}
