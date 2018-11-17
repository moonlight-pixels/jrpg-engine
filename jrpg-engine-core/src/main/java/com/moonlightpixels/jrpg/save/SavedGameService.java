package com.moonlightpixels.jrpg.save;

import com.moonlightpixels.jrpg.GameState;

import java.util.Optional;

/**
 * Serivce for Saving/Loading game save states.
 */
public interface SavedGameService {
    /**
     * Saves the given GameState object.
     *
     * @param gameState GameState
     * @return true if save was successful, false otherwise
     */
    boolean save(GameState gameState);

    /**
     * Loads game state. Returns empty Optional if load fails.
     *
     * @param saveId ID of save file to load
     * @return Gamestate if load was successful
     */
    Optional<GameState> load(String saveId);
}
