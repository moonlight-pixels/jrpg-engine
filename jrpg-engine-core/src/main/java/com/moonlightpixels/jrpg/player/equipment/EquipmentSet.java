package com.moonlightpixels.jrpg.player.equipment;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
@SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
public final class EquipmentSet {
    private final List<SlotAssignement> equipmentSlots;

    /**
     * Creates an EquipmentSet with the given slots.
     *
     * @param equipmentSlots Slots in this set
     */
    public EquipmentSet(final List<EquipmentSlot> equipmentSlots) {
        this.equipmentSlots = equipmentSlots.stream()
            .map(SlotAssignement::new)
            .collect(Collectors.toList());
    }

    /**
     * Get the Slot Assignments belonging to this set.
     *
     * @return Unmodifiable List of SlotAssignments
     */
    public List<SlotAssignement> getEquipmentSlots() {
        return Collections.unmodifiableList(equipmentSlots);
    }

    @EqualsAndHashCode
    @ToString
    public static final class SlotAssignement {
        private final EquipmentSlot slot;
        private Equipment equipment;

        private SlotAssignement(final EquipmentSlot slot) {
            this.slot = slot;
        }

        /**
         * Get Slot.
         *
         * @return Slot
         */
        public EquipmentSlot getSlot() {
            return slot;
        }

        /**
         * Get Equipment assigned to this Slot.
         *
         * @return Equipment as Optional
         */
        public Optional<Equipment> getEquipment() {
            return Optional.ofNullable(equipment);
        }

        /**
         * Sets the equipment assigned to this slot and returns the equipment previously assigned, if any.
         *
         * @param equipment New equipment to set
         * @return previous equipment
         */
        public Optional<Equipment> setEquipment(final Equipment equipment) {
            Preconditions.checkArgument(
                slot.canEquip(equipment),
                String.format(
                    "Equipment of type [%s] cannot be equiped in slot [%s]",
                    equipment.getType(),
                    slot.getLabel()
                )
            );
            final Equipment previous = this.equipment;
            this.equipment = equipment;
            return Optional.ofNullable(previous);
        }
    }
}
