package com.moonlightpixels.jrpg.ui.standard.action;

import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.ui.SelectList;

import javax.inject.Inject;

/**
 * Exits game.
 */
public final class ExitGameAction implements SelectList.Action {
    private final GdxFacade gdx;

    /**
     * Creates a new ExitGameAction.
     *
     * @param gdx GdxFacade to use for GDX interactions
     */
    @Inject
    public ExitGameAction(final GdxFacade gdx) {
        this.gdx = gdx;
    }

    /**
     * Exits game.
     */
    @Override
    public void perform() {
        gdx.getApp().exit();
    }
}
