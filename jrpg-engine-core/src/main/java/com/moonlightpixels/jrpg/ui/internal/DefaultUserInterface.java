package com.moonlightpixels.jrpg.ui.internal;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;
import com.moonlightpixels.jrpg.internal.inject.GraphicsModule;
import com.moonlightpixels.jrpg.ui.UiStyle;
import com.moonlightpixels.jrpg.ui.UserInterface;

import javax.inject.Inject;
import javax.inject.Named;

public final class DefaultUserInterface implements UserInterface {
    private final GraphicsContext graphicsContext;
    private final Stage stage;
    private final GdxFacade gdx;
    private final UiStyle uiStyle;

    @Inject
    public DefaultUserInterface(@Named(GraphicsModule.UI) final GraphicsContext graphicsContext,
                                final GdxFacade gdx,
                                final UiStyle uiStyle) {
        this.graphicsContext = graphicsContext;
        this.stage = graphicsContext.createStage();
        this.gdx = gdx;
        this.uiStyle = uiStyle;
    }

    @Override
    public void clear() {
        stage.clear();
    }

    @Override
    public void add(final Actor actor) {
        stage.addActor(actor);
    }

    @Override
    public void remove(final Actor actor) {
        stage.getRoot().removeActor(actor);
    }

    @Override
    public void update() {
        stage.act(gdx.getGraphics().getDeltaTime());
        graphicsContext.activate();
        stage.draw();
    }

    @Override
    public UiStyle getUiStyle() {
        return uiStyle;
    }
}
