package com.github.jaystgelais.jrpg.ui.menu;

import com.github.jaystgelais.jrpg.Game;

public final class ExitGameAction implements MenuAction {
    @Override
    public void perform(final Menu menu) {
        Game.getInstance().exitGame();
    }
}