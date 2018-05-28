package com.moonlightpixels.jrpg.ui.internal;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.internal.inject.GraphicsModule;
import com.moonlightpixels.jrpg.ui.UserInterface;

import javax.inject.Inject;
import javax.inject.Named;

public final class DefaultUserInterface implements UserInterface {
    private final Camera camera;
    private final Stage stage;
    private final GdxFacade gdx;
    private final SpriteBatch spriteBatch;

    @Inject
    public DefaultUserInterface(@Named(GraphicsModule.UI_CAMERA) final Camera camera, final Stage stage,
                                final GdxFacade gdx, final SpriteBatch spriteBatch) {
        this.camera = camera;
        this.stage = stage;
        this.spriteBatch = spriteBatch;
        this.gdx = gdx;
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
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.draw();
    }
}
