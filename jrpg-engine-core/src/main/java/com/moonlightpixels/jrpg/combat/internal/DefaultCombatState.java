package com.moonlightpixels.jrpg.combat.internal;

import com.badlogic.gdx.ai.msg.Telegram;
import com.moonlightpixels.jrpg.combat.CombatState;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.internal.JRPG;

public final class DefaultCombatState implements CombatState {
    @Override
    public void enter(final JRPG entity) {

    }

    @Override
    public void update(final JRPG entity) {

    }

    @Override
    public void exit(final JRPG entity) {

    }

    @Override
    public boolean onMessage(final JRPG entity, final Telegram telegram) {
        return false;
    }

    @Override
    public boolean handleControlEvent(final ControlEvent event) {
        return false;
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        return false;
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) {

    }
}
