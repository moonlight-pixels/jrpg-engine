package com.github.jaystgelais.jrpg.inventory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ItemRegistry {
    private static final Map<String, Item> items = new HashMap<>();

    private ItemRegistry() { }

    public static void addItem(final Item item) {
        if (items.containsKey(item.getId())) {
            throw new IllegalStateException(String.format("Item with ID [%s] already exists.", item.getId()));
        }

        items.put(item.getId(), item);
    }

    public static Item getItem(final String id) {
        if (!items.containsKey(id)) {
            throw new IllegalStateException(String.format("Item with ID [%s] does not exist.", id));
        }

        return items.get(id);
    }

    public static Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }
}
