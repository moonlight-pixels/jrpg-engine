package com.moonlightpixels.jrpg.ui.menu;

import com.moonlightpixels.jrpg.Game;

public final class ExitGameAction implements MenuAction {
    @Override
    public void perform(final Menu menu) {
        Game.getInstance().exitGame();
    }
}
