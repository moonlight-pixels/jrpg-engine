package com.moonlightpixels.jrpg.inventory;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public interface Item {
    /**
     * Key that uniquely identifies this Item in the game.
     *
     * @return Key
     */
    Key getKey();

    /**
     * This Item's type.
     *
     * @return Item Type
     */
    ItemType getType();

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    interface Key { }
}
