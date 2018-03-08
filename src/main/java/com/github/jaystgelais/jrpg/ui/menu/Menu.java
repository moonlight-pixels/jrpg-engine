package com.github.jaystgelais.jrpg.ui.menu;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.HashSet;
import java.util.Set;

public abstract class Menu implements InputHandler, Updatable {
    private final WidgetGroup layout;
    private boolean active = true;
    private final Set<Updatable> updatableChildren = new HashSet<>();
    private Menu childMenu;

    public Menu() {
        layout = createLayout();
    }

    protected abstract WidgetGroup createLayout();

    protected abstract InputHandler getActiveInputHandler();

    public final WidgetGroup getLayout() {
        return layout;
    }

    protected final void registerUpdatableChild(final Updatable updatable) {
        updatableChildren.add(updatable);
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
    public final void update(final long elapsedTime) {
        if (active) {
            if (childMenu != null) {
                updateChildMenu(elapsedTime);
            } else {
                updatableChildren.forEach(updatable -> updatable.update(elapsedTime));
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
