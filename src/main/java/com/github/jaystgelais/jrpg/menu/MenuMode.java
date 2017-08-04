package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;

import java.util.Map;
import java.util.Stack;

public final class MenuMode extends GameMode {
    private final Stack<Menu> menus = new Stack<>();
    private final MenuDefinition initialMenu;

    public MenuMode(final MenuDefinition initialMenu) {
        this.initialMenu = initialMenu;
    }

    @Override
    public String getKey() {
        return "menu";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        menus.push(initialMenu.getMenu(getGame().getGraphicsService()));
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        if (!menus.isEmpty()) {
            graphicsService.renderStart();
            menus.peek().render(graphicsService);
            graphicsService.renderEnd();
        }
    }

    @Override
    public void update(final long elapsedTime) {
        if (menus.isEmpty()) {
            exitGameMode();
        } else if (!menus.peek().isActive()) {
            menus.pop();
        } else {
            menus.peek().update(elapsedTime);
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (!menus.isEmpty()) {
            menus.peek().handleInput(inputService);
        }
    }
}
