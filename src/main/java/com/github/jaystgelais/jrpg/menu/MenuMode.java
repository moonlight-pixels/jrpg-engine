package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.GameMode;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;

import java.util.Map;
import java.util.Stack;

public final class MenuMode extends GameMode {
    private final Stack<Menu> menus = new Stack<>();
    private final MenuDefinition initialMenu;

    private DelayedInput cancelInput = new DelayedInput(Inputs.CANCEL);

    public MenuMode(final MenuDefinition initialMenu) {
        this.initialMenu = initialMenu;
    }

    @Override
    public String getKey() {
        return "menu";
    }

    @Override
    public void onEnter(final Map<String, Object> params) {
        final Menu menu = initialMenu.getMenu(getGame().getGraphicsService());
        menu.setCancelInput(cancelInput);
        menus.push(menu);
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
        GameState.incrementTimePLayed(elapsedTime);
        if (menus.isEmpty()) {
            exitGameMode();
        } else if (!menus.peek().isActive()) {
            menus.pop();
        } else {
            MenuAction action = menus.peek().getAndClearAction();
            if (action != null) {
                action.perform(this);
            } else {
                menus.peek().update(elapsedTime);
            }
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (!menus.isEmpty()) {
            menus.peek().handleInput(inputService);
        }
    }

    void openMenu(final Menu menu) {
        menu.setCancelInput(cancelInput);
        menus.push(menu);
    }
}
