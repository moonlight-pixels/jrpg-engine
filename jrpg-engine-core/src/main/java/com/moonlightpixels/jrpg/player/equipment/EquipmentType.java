package com.moonlightpixels.jrpg.player.equipment;

import com.moonlightpixels.jrpg.inventory.ItemType;
import com.moonlightpixels.jrpg.player.PlayerCharacter;

public interface EquipmentType extends ItemType {
    /**
     * Returns true if the given player can equip this type.
     *
     * @param player PLayer to test
     * @return true if the given player can equip this type
     */
    boolean canEquip(PlayerCharacter player);
}
