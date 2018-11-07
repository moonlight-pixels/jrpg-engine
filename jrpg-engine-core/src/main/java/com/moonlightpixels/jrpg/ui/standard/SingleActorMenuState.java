package com.moonlightpixels.jrpg.ui.standard;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.ui.InputHandler;
import com.moonlightpixels.jrpg.ui.Menu;
import com.moonlightpixels.jrpg.ui.MenuState;
import lombok.Data;

@Data
public final class SingleActorMenuState implements MenuState {
    private final SingleActorMenuProvider singleActorMenuProvider;
    private final boolean dismissible;
    private Actor providedActor;

    @Override
    public InputHandler getInputHandler() {
        return singleActorMenuProvider.getInputHandler();
    }

    @Override
    public boolean isDismissible() {
        return dismissible;
    }

    @Override
    public void enter(final Menu menu) {
        providedActor = singleActorMenuProvider.getActor(menu.getUserInterface().getUiStyle());
        menu.addActor(providedActor);
    }

    @Override
    public void update(final Menu menu) { }

    @Override
    public void exit(final Menu menu) {
        menu.removeActor(providedActor);
        providedActor = null;
    }

    @Override
    public boolean onMessage(final Menu menu, final Telegram telegram) {
        return false;
    }
}
