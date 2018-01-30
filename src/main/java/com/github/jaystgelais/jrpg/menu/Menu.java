package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.ui.Layout;

public abstract class Menu implements InputHandler, Renderable, Updatable {
    private final Layout layout;
    private boolean active = true;
    private Menu childMenu;

    public Menu() {
        layout = createLayout();
    }

    protected abstract Layout createLayout();

    protected abstract InputHandler getActiveInputHandler();

    protected final Layout getLayout() {
        return layout;
    }

    public final boolean isActive() {
        return active;
    }

    public final void performAction(final MenuAction action) {
        action.perform(this);
    }

    public final void setChildMenu(final Menu childMenu) {
        this.childMenu = childMenu;
    }

    @Override
    public final void handleInput(final InputService inputService) {
        if (active) {
            if (childMenu != null) {
                childMenu.handleInput(inputService);
            } else if (inputService.isPressed(Inputs.CANCEL)) {
                active = false;
            } else {
                InputHandler inputHandler = getActiveInputHandler();
                if (inputHandler != null) {
                    inputHandler.handleInput(inputService);
                }
            }
        }
    }

    @Override
    public final void render(final GraphicsService graphicsService) {
        if (active) {
            if (childMenu != null) {
                childMenu.render(graphicsService);
            } else {
                getLayout().render(graphicsService);
            }
        }
    }

    @Override
    public final void dispose() {
        getLayout().dispose();
    }

    @Override
    public final void update(final long elapsedTime) {
        if (active) {
            if (childMenu != null) {
                updateChildMenu(elapsedTime);
            } else {
                getLayout().update(elapsedTime);
            }
        }
    }

    private void updateChildMenu(final long elapsedTime) {
        if (childMenu.isActive()) {
            childMenu.update(elapsedTime);
        } else {
            childMenu = null;
        }
    }
}
