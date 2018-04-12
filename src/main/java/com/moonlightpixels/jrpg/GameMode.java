package com.moonlightpixels.jrpg;

import com.moonlightpixels.jrpg.state.StateAdapter;

public abstract class GameMode extends StateAdapter {

    private Game game;

    public final Game getGame() {
        return game;
    }

    final void setGame(final Game game) {
        this.game = game;
    }

    public final void exitGameMode() {
        game.exitCurrentGameMode();
    }
}
