package com.moonlightpixels.jrpg.legacy.ui.menu;

public final class OpenMenuAction implements MenuAction {
    private final MenuDefinition menuDefinition;

    public OpenMenuAction(final MenuDefinition menuDefinition) {
        this.menuDefinition = menuDefinition;
    }

    @Override
    public void perform(final Menu menu) {
        menu.setChildMenu(menuDefinition.getMenu());
    }
}
