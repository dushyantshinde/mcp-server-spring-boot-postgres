package com.ds.mcp_qty_item.repo;

import com.ds.mcp_qty_item.entity.QuantityItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuantityItemRepository extends JpaRepository<QuantityItem, Long> {
    Optional<QuantityItem> findByNameIgnoreCase(String name);
}