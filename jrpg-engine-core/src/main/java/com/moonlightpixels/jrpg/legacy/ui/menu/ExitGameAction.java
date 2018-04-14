package com.moonlightpixels.jrpg.legacy.ui.menu;

import com.moonlightpixels.jrpg.legacy.Game;

public final class ExitGameAction implements MenuAction {
    @Override
    public void perform(final Menu menu) {
        Game.getInstance().exitGame();
    }
}
