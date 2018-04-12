package com.moonlightpixels.jrpg.inventory;

public interface InventoryContent {
    String getName();
    void addToInventory(Inventory inventory, int quantity);
}
