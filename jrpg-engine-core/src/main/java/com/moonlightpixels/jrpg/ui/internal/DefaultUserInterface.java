package com.moonlightpixels.jrpg.ui.internal;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.common.collect.Lists;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;
import com.moonlightpixels.jrpg.ui.UserInterface;

import java.util.List;

public final class DefaultUserInterface implements UserInterface {
    private final Camera camera;
    private final Stage stage;
    private final GdxFacade gdx;
    private final SpriteBatch spriteBatch;

    public DefaultUserInterface(final Camera camera, final Stage stage,
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
        List<Actor> actors = Lists.newLinkedList(stage.getActors());
        actors.remove(actor);
        stage.clear();
        actors.forEach(stage::addActor);
    }

    @Override
    public void update() {
        stage.act(gdx.getGraphics().getDeltaTime());
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.draw();
    }
}
