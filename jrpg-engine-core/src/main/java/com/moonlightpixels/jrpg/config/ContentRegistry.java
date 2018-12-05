package com.moonlightpixels.jrpg.config;

import com.moonlightpixels.jrpg.animation.AnimationSetProvider;
import com.moonlightpixels.jrpg.inventory.Item;
import com.moonlightpixels.jrpg.map.MapDefinition;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.equipment.EquipmentSlot;

/**
 * Register Game Content.
 */
public interface ContentRegistry {
    /**
     * Registers a CharacterAnimationSet with a provider that can initialize the animations after the graphics system
     * has been initailized.
     *
     * @param key Key to look this Animaton set up
     * @param provider AnimationSet provider
     */
    void register(CharacterAnimationSet.Key key, AnimationSetProvider<CharacterAnimationSet> provider);

    /**
     * Register a map definition.
     *
     * @param map MapDefinition
     */
    void register(MapDefinition map);

    /**
     * Register an item for the Inventory System.
     *
     * @param item Item
     */
    void register(Item item);

    /**
     * Registers the list of EquipmentSlots used by players.
     *
     * @param equipmentSlots EquipmentSlot to register
     */
    void registerEquimentSlots(EquipmentSlot... equipmentSlots);
}
