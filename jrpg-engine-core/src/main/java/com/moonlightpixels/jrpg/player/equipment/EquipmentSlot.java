package com.moonlightpixels.jrpg.player.equipment;

import java.util.List;

public interface EquipmentSlot {
    /**
     * Get the label to be used in menus for this equipment slot.
     *
     * @return label
     */
    String getLabel();

    /**
     * Get a list of allowed EquipmentTypes that can be set in this slot.
     *
     * @return Allowed Equipment Types
     */
    List<EquipmentType> getAllowedTypes();

    /**
     * Returns true if Equipment's type is in list of allowed types for this slot.
     *
     * @param equipment Equipment to test
     * @return true if Equipment's type is in list of allowed types for this slot
     */
    default boolean canEquip(final Equipment equipment) {
        return getAllowedTypes().contains(equipment.getType());
    }
}
