package com.github.jaystgelais.jrpg.inventory;

public interface InventoryContent {
    String getName();
    void addToInventory(Inventory inventory, int quantity);
}
