package com.alvy.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "request_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private SupplyRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private SupplyItem item;

    @Column(nullable = false)
    private int quantityRequested;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private boolean onHold = false;

    @Column(nullable = false)
    private boolean fulfilled = false;

    public void holdInventory() {
        if (!onHold && item != null) {
            item.holdQuantity(quantityRequested);
            this.onHold = true;
        }
    }

    public void markAsFulfilled() {
        if (!onHold) {
            throw new IllegalStateException("Cannot fulfill item that is not on hold");
        }
        if (item != null) {
            item.fulfillHold(quantityRequested);
        }
        this.fulfilled = true;
    }

    public void releaseHold() {
        if (onHold && !fulfilled && item != null) {
            item.releaseHold(quantityRequested);
            this.onHold = false;
        }
    }
}
