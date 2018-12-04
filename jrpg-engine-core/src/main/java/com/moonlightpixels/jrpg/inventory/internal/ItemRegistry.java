package com.moonlightpixels.jrpg.inventory.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.inventory.Item;
import com.moonlightpixels.jrpg.player.equipment.Equipment;

import java.util.HashMap;
import java.util.Map;

public final class ItemRegistry {
    private final Map<Item.Key, Item> items = new HashMap<>();

    public void register(final Item item) {
        items.put(item.getKey(), item);
    }

    public Item getItem(final Item.Key key) {
        Preconditions.checkState(items.containsKey(key), String.format("Item with key {%s} does not exist", key));
        return items.get(key);
    }

    public Equipment getEquipment(final Equipment.Key key) {
        return (Equipment) getItem(key);
    }
}
