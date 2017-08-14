package com.github.jaystgelais.jrpg.menu;

public final class ExitGameAction implements MenuAction {
    @Override
    public void perform(final MenuMode mode) {
        mode.getGame().exitGame();
    }
}
