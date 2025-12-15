package com.alvy.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supply_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clinicianName;

    @Column(nullable = false)
    private String clinicianEmail;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private LocalDate programStart;

    @Column(nullable = false)
    private String programType;

    @Column(nullable = false)
    private LocalDateTime submitted;

    @Column(nullable = false)
    private String status = "PENDING";

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestItem> requestItems = new ArrayList<>();

    public void addRequestItem(RequestItem item) {
        requestItems.add(item);
        item.setRequest(this);
    }

    public void submit() {
        if (!"PENDING".equals(this.status)) {
            throw new IllegalStateException("Can only submit pending requests");
        }
        requestItems.forEach(RequestItem::holdInventory);
    }

    public void approve() {
        if (!"PENDING".equals(this.status)) {
            throw new IllegalStateException("Can only approve pending requests");
        }
        this.status = "APPROVED";
    }

    public void fulfill() {
        if (!"APPROVED".equals(this.status)) {
            throw new IllegalStateException("Can only fulfill approved requests");
        }
        this.status = "FULFILLED";
        requestItems.forEach(RequestItem::markAsFulfilled);
    }

    public void cancel() {
        if ("FULFILLED".equals(this.status)) {
            throw new IllegalStateException("Cannot cancel fulfilled requests");
        }
        this.status = "CANCELLED";
        requestItems.forEach(RequestItem::releaseHold);
    }

    @PrePersist
    protected void onCreate() {
        if (submitted == null) {
            submitted = LocalDateTime.now();
        }
    }
}
