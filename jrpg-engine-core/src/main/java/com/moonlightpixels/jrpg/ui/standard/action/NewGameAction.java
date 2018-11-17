package com.moonlightpixels.jrpg.ui.standard.action;

import com.moonlightpixels.jrpg.GameState;
import com.moonlightpixels.jrpg.config.internal.ConfigurationHandler;
import com.moonlightpixels.jrpg.internal.DefaultGameState;
import com.moonlightpixels.jrpg.internal.GameStateHolder;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.ui.SelectList;

import javax.inject.Inject;

/**
 * Starts a new game.
 */
public final class NewGameAction implements SelectList.Action {
    private final JRPG jrpg;
    private final GameStateHolder gameStateHolder;
    private final ConfigurationHandler configurationHandler;

    /**
     * Creates a new NewGameAction.
     *
     * @param jrpg Reference to running JRPG instance
     * @param gameStateHolder Gamestate Holder singleton
     * @param configurationHandler Configuration handler that will configure new game state
     */
    @Inject
    public NewGameAction(final JRPG jrpg,
                         final GameStateHolder gameStateHolder,
                         final ConfigurationHandler configurationHandler) {
        this.jrpg = jrpg;
        this.gameStateHolder = gameStateHolder;
        this.configurationHandler = configurationHandler;
    }

    /**
     * Starts a new game.
     */
    @Override
    public void perform() {
        GameState gameState = new DefaultGameState();
        configurationHandler.configureNewGame(gameState);
        gameStateHolder.setGameState(gameState);
        jrpg.toMap();
    }
}
