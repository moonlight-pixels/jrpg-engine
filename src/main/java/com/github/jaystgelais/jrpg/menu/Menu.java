package com.github.jaystgelais.jrpg.menu;

import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.ui.Layout;

public abstract class Menu implements InputHandler, Renderable, Updatable {
    private boolean active = true;
    private MenuAction action;

    public Menu() {

    }

    protected abstract Layout getLayout();

    protected abstract InputHandler getActiveInputHandler();

    public final boolean isActive() {
        return active;
    }

    public final MenuAction getAndClearAction() {
        MenuAction currentAction = action;
        action = null;
        return currentAction;
    }

    public final void setAction(final MenuAction action) {
        if (this.action == null) {
            this.action = action;
        }
    }

    @Override
    public final void handleInput(final InputService inputService) {
        if (inputService.isPressed(Inputs.CANCEL)) {
            active = false;
        } else {
            InputHandler inputHandler = getActiveInputHandler();
            if (inputHandler != null) {
                inputHandler.handleInput(inputService);
            }
        }
    }

    @Override
    public final void render(final GraphicsService graphicsService) {
        if (active) {
            getLayout().render(graphicsService);
        }
    }

    @Override
    public final void dispose() {
        getLayout().dispose();
    }

    @Override
    public final void update(final long elapsedTime) {
        if (active) {
            getLayout().update(elapsedTime);
        }
    }
}
