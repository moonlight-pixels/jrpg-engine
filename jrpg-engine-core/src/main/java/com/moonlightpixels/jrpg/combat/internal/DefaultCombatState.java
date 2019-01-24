package com.moonlightpixels.jrpg.combat.internal;

import com.badlogic.gdx.ai.msg.Telegram;
import com.moonlightpixels.jrpg.combat.CombatState;
import com.moonlightpixels.jrpg.combat.render.internal.BattleRenderer;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;

import javax.inject.Inject;

public final class DefaultCombatState implements CombatState {
    private final GdxFacade gdx;
    private final BattleRenderer renderer;

    @Inject
    public DefaultCombatState(final GdxFacade gdx, final BattleRenderer renderer) {
        this.gdx = gdx;
        this.renderer = renderer;
    }

    @Override
    public void enter(final JRPG entity) {
        renderer.reset();
    }

    @Override
    public void update(final JRPG entity) {
        renderer.update(gdx.getGraphics().getDeltaTime());
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
