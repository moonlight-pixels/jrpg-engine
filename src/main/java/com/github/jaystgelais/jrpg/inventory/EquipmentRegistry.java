package com.github.jaystgelais.jrpg.inventory;

import java.util.HashMap;
import java.util.Map;

public final class EquipmentRegistry {
    private static final Map<String, Equipment> EQUIPMENT = new HashMap<>();

    private EquipmentRegistry() { }

    public static void addEquipment(final Equipment equipment) {
        if (EQUIPMENT.containsKey(equipment.getId())) {
            throw new IllegalStateException(String.format("Equipment with ID [%s] already exists.", equipment.getId()));
        }

        EQUIPMENT.put(equipment.getId(), equipment);
    }

    public static Equipment getEquipment(final String id) {
        if (!EQUIPMENT.containsKey(id)) {
            throw new IllegalStateException(String.format("Equipment with ID [%s] does not exist.", id));
        }

        return EQUIPMENT.get(id);
    }
}
