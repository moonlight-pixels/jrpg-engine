package com.moonlightpixels.jrpg.frontend.internal;

import com.badlogic.gdx.ai.msg.Telegram;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.ui.UserInterface;

import javax.inject.Inject;

public final class DefaultFrontEndState implements FrontEndState {
    private final UserInterface userInterface;

    @Inject
    public DefaultFrontEndState(final UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void enter(final JRPG entity) {

    }

    @Override
    public void update(final JRPG entity) {
        userInterface.update();
    }

    @Override
    public void exit(final JRPG entity) {

    }

    @Override
    public boolean onMessage(final JRPG entity, final Telegram telegram) {
        return false;
    }
}
