package com.moonlightpixels.jrpg;

import com.moonlightpixels.jrpg.map.Location;
import com.moonlightpixels.jrpg.player.Cast;

import java.time.Duration;
import java.util.Optional;

/**
 * Stores current Game state.
 */
public interface GameState {
    /**
     * The saveId this GameState was loaded from, or the last saveId it was saved to.
     *
     * @return default saveId
     */
    Optional<String> getDefaultSaveId();

    /**
     * Sets a new default saveId.
     *
     * @param saveId new default saveId
     */
    void setDefaultSaveId(String saveId);

    /**
     * Time game has been played.
     *
     * @return duration of time played
     */
    Duration getTimePlayed();

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

    /**
     * Validates that the GameState meets all requirements of a valid state. Requirements include:
     *
     * <ul>
     *     <li>There is a valid active Party</li>
     * </ul>
     *
     * @return true is Gamestate is valid.
     */
    boolean isValid();

    /**
     * Called each game loop iteration.
     *
     * @param delta time elapsed since last game loop iteration
     */
    void update(float delta);
}
