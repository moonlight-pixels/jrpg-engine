package com.moonlightpixels.jrpg;

import com.moonlightpixels.jrpg.map.Location;
import com.moonlightpixels.jrpg.player.Cast;

/**
 * Stores current Game state.
 */
public interface GameState {
    /**
     * Returns the current location of the player in the game world.
     *
     * @return player location
     */
    Location getLocation();

    /**
     * Sets the current location of the player in the game world.
     *
     * @param location player location
     */
    void setLocation(Location location);

    /**
     * Returns the games Cast of player characters.
     *
     * @return Cast
     */
    Cast getCast();
}
