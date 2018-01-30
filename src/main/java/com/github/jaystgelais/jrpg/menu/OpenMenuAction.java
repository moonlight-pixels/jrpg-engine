package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.Game;

public final class OpenMenuAction implements MenuAction {
    private final MenuDefinition menuDefinition;

    public OpenMenuAction(final MenuDefinition menuDefinition) {
        this.menuDefinition = menuDefinition;
    }

    @Override
    public void perform(final Menu menu) {
        menu.setChildMenu(menuDefinition.getMenu(Game.getInstance().getGraphicsService()));
    }
}
