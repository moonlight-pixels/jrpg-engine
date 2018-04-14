package com.moonlightpixels.jrpg.legacy.inventory;

public interface InventoryContent {
    String getName();
    void addToInventory(Inventory inventory, int quantity);
}
