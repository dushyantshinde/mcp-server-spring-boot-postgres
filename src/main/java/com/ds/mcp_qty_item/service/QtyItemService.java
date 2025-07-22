package com.ds.mcp_qty_item.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ds.mcp_qty_item.entity.QuantityItem;
import com.ds.mcp_qty_item.entity.QuantityItemAllocation;
import com.ds.mcp_qty_item.repo.QuantityItemAllocationRepository;
import com.ds.mcp_qty_item.repo.QuantityItemRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QtyItemService {

  @Autowired
  private QuantityItemRepository quantityItemRepository;

  @Autowired
  private QuantityItemAllocationRepository allocationRepository;

  @Tool(name = "addItem",
          description = "Add an item to the quantity list or update its quantity. Specify item name and quantity.")
  public String addItem(String name, int quantity) {
    if (name == null || name.trim().isEmpty() || quantity <= 0) {
      return "Error: Invalid item name or quantity.";
    }
    return quantityItemRepository.findByNameIgnoreCase(name)
            .map(existingItem -> {
              existingItem.setQuantity(existingItem.getQuantity() + quantity);
              existingItem.setUpdatedAt(LocalDateTime.now());
              quantityItemRepository.save(existingItem);
              return "Updated " + name + " quantity to " + existingItem.getQuantity() + ".";
            })
            .orElseGet(() -> {
              quantityItemRepository.save(new QuantityItem(name, quantity));
              return "Added " + quantity + " of " + name + " to the quantity list.";
            });
  }

  @Tool(name = "getItems",
          description = "Get all items currently in the quantity list. Returns a list of items with their names and quantities.")
  public List<QuantityItem> getItems() {
    try {
      return quantityItemRepository.findAll();
    } catch (Exception e) {
      return List.of();
    }
  }

    public List<QuantityItem> getItemsForREST() {
        try {
            return quantityItemRepository.findAll();
        } catch (Exception e) {
            return List.of();
        }
    }

  @Tool(name = "removeItem",
          description = "Remove a specified quantity of an item from the quantity list. Specify item name and quantity to remove. If quantity is not specified or is greater than item quantity, the item is removed.")
  public String removeItem(String name, int quantity) {
    if (name == null || name.trim().isEmpty()) {
      return "Error: Invalid item name.";
    }
    try {
      return quantityItemRepository.findByNameIgnoreCase(name)
              .map(item -> {
                if (quantity <= 0 || quantity >= item.getQuantity()) {
                  quantityItemRepository.delete(item);
                  return "Removed item '" + name + "' from the quantity list.";
                } else {
                  item.setQuantity(item.getQuantity() - quantity);
                  quantityItemRepository.save(item);
                  return "Removed " + quantity + " of '" + name + "'. Remaining: " + item.getQuantity();
                }
              })
              .orElse("Error: Item '" + name + "' not found in the quantity list.");
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  @Tool(name = "addAllocation",
          description = "Add an allocation for a quantity item. Specify item name, allocation quantity, and status.")
  public String addAllocation(String itemName, int allocationQuantity, String status) {
    if (itemName == null || itemName.trim().isEmpty() || allocationQuantity <= 0 || status == null || status.trim().isEmpty()) {
      return "Error: Invalid allocation details.";
    }
    return quantityItemRepository.findByNameIgnoreCase(itemName)
            .map(item -> {
              QuantityItemAllocation allocation = new QuantityItemAllocation(item, allocationQuantity, status);
              allocationRepository.save(allocation);
              // Update item quantity if status is 'consumed'
              if ("consumed".equalsIgnoreCase(status)) {
                item.setQuantity(item.getQuantity() - allocationQuantity);
                item.setUpdatedAt(LocalDateTime.now());
                quantityItemRepository.save(item);
              }
              return "Allocation added for item '" + itemName + "' with status '" + status + "'.";
            })
            .orElse("Error: Item '" + itemName + "' not found.");
  }

  @Tool(name = "getAllocations",
          description = "Get all allocations for a quantity item by name.")
  public List<QuantityItemAllocation> getAllocations(String itemName) {
    return quantityItemRepository.findByNameIgnoreCase(itemName)
            .map(QuantityItem::getAllocations)
            .orElse(List.of());
  }

  @Tool(name = "removeAllocation",
          description = "Remove an allocation by allocation ID.")
  public String removeAllocation(Long allocationId) {
    return allocationRepository.findById(allocationId)
            .map(allocation -> {
              allocationRepository.delete(allocation);
              return "Allocation removed.";
            })
            .orElse("Error: Allocation not found.");
  }
}
