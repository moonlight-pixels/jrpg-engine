package com.moonlightpixels.jrpg.player;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.combat.stats.StatHolder;
import com.moonlightpixels.jrpg.combat.stats.StatMeter;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.equipment.EquipmentSet;
import com.moonlightpixels.jrpg.player.internal.DefaultPlayerCharacter;

public interface PlayerCharacter extends StatHolder {
    /**
     * Unique key for this PlayerCharacter.
     *
     * @return keu
     */
    Key getKey();

    /**
     * PlayerCharacter's name used in menus and dialog.
     *
     * @return name
     */
    String getName();

    /**
     * PlayerCharacter's name used in menus and dialog.
     *
     * @param name name
     */
    void setName(String name);

    /**
     * Key mapped to this PlayerCharacter's animation set.
     *
     * @return animation set key
     */
    CharacterAnimationSet.Key getAnimationSet();

    /**
     * Key mapped to this PlayerCharacter's animation set.
     *
     * @param animationSet animation set key
     */
    void setAnimationSet(CharacterAnimationSet.Key animationSet);

    /**
     * Returns function used to calculate experience needed to reach next level.
     *
     * @return NextLevelFunction
     */
    NextLevelFunction getNextLevelFunction();

    /**
     * PlayerCharacter's current experience.
     *
     * @return experience
     */
    int getExperience();

    /**
     * Set PlayerCharacter's current experience.
     *
     * @param experience experience
     */
    void setExperience(int experience);

    /**
     * PLayers currently equiped equipment.
     *
     * @return EquipmentSet
     */
    EquipmentSet getEquipmentSet();

    /**
     * PlayerCharacter's hitpoint meter.
     *
     * @return hit points
     */
    StatMeter getHitPoints();

    /**
     * Returns stat meter based on stat key for max value stat.
     *
     * @param maxValueKey max value stat key
     * @return StatMeter
     */
    StatMeter getMeter(Stat.Key maxValueKey);

    /**
     * Adds a StatMeter with a given max value stat.
     *
     * @param maxValueKey Key for max value stat
     */
    void addMeter(Stat.Key maxValueKey);

    /**
     * Set base value for a stat.
     *
     * @param key stat
     * @param value base value
     */
    void setBaseValue(Stat.Key key, int value);

    /**
     * Get the final calculated value for a stat.
     *
     * @param key stat
     * @return final calculated value
     */
    int getStatValue(Stat.Key key);

    /**
     * Returns the default builder for PlayerCharacters.
     *
     * @return builder
     */
    static DefaultPlayerCharacter.DefaultPlayerCharacterBuilder builder() {
        return DefaultPlayerCharacter.builder();
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    interface Key { }
}
