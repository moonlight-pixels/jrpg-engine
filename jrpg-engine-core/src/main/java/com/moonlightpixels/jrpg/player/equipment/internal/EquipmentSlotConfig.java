package com.moonlightpixels.jrpg.player.equipment.internal;

import com.moonlightpixels.jrpg.player.equipment.EquipmentSlot;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class EquipmentSlotConfig {
    private List<EquipmentSlot> equipmentSlots = Collections.emptyList();
}
