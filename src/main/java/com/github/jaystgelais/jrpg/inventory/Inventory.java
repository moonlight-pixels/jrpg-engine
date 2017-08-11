package com.github.jaystgelais.jrpg.inventory;

import com.github.jaystgelais.jrpg.party.Character;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Inventory {
    public static final int DEFAULT_MAX_QUANTITY_PER_ITEM = 99;

    private final Map<Item, Integer> items = new HashMap<>();
    private final int maxQuantityPerItem;

    public Inventory(final int maxQuantityPerItem) {
        this.maxQuantityPerItem = maxQuantityPerItem;
    }

    public Inventory() {
        this(DEFAULT_MAX_QUANTITY_PER_ITEM);
    }

    public boolean useItem(final Item item, final Character target) {
        if (item.doesUsageConsumesItem() && getQuantity(item) < 1) {
            return false;
        }
        item.getAction().perform(target);

        return !item.doesUsageConsumesItem() || adjustQuantity(item, -1);
    }

    public boolean adjustQuantity(final Item item, final int change) {
        final int newQuantity = getQuantity(item) + change;
        if (newQuantity < 0 || newQuantity > maxQuantityPerItem) {
            return false;
        } else if (newQuantity == 0) {
            items.remove(item);
        } else {
            items.put(item, newQuantity);
        }

        return true;
    }

    public int getQuantity(final Item item) {
        return items.containsKey(item) ? items.get(item) : 0;
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items.keySet());
    }
}
