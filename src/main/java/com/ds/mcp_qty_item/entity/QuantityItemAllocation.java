package com.ds.mcp_qty_item.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_item_allocations")
public class QuantityItemAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quantity_item_id")
    private QuantityItem quantityItem;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String status; // e.g. consumed, available, retired, disposed

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public QuantityItemAllocation() {}

    public QuantityItemAllocation(QuantityItem quantityItem, int quantity, String status) {
        this.quantityItem = quantityItem;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public QuantityItem getQuantityItem() { return quantityItem; }
    public void setQuantityItem(QuantityItem quantityItem) { this.quantityItem = quantityItem; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

