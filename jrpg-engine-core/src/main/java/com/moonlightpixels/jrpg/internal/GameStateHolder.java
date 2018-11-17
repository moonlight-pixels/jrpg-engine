package com.moonlightpixels.jrpg.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.GameState;

public final class GameStateHolder {
    private GameState gameState;

    public GameState getGameState() {
        Preconditions.checkState(gameState != null, "No currently held GameState");
        return gameState;
    }

    public void setGameState(final GameState gameState) {
        Preconditions.checkArgument(gameState.isValid(), "gameState must be valid");
        this.gameState = gameState;
    }
}
