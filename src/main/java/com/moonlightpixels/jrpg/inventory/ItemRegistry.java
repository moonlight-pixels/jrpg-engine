package com.moonlightpixels.jrpg.inventory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ItemRegistry {
    private static final Map<String, Item> ITEMS = new HashMap<>();

    private ItemRegistry() { }

    public static void addItem(final Item item) {
        if (ITEMS.containsKey(item.getId())) {
            throw new IllegalStateException(String.format("Item with ID [%s] already exists.", item.getId()));
        }

        ITEMS.put(item.getId(), item);
    }

    public static Item getItem(final String id) {
        if (!ITEMS.containsKey(id)) {
            throw new IllegalStateException(String.format("Item with ID [%s] does not exist.", id));
        }

        return ITEMS.get(id);
    }

    public static Collection<Item> getItems() {
        return Collections.unmodifiableCollection(ITEMS.values());
    }
}
