package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.graphics.Renderable;
import com.moonlightpixels.jrpg.state.Updatable;
import com.moonlightpixels.jrpg.util.TimeUtil;
import com.google.common.collect.Lists;

import java.util.List;

public final class UserInterface implements Renderable, Updatable {
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Stage stage;

    public UserInterface(final int width, final int height) {
        camera = new OrthographicCamera(width, height);
        viewport = new FitViewport(width, height, camera);
        camera.update();
    }

    public void init(final SpriteBatch spriteBatch) {
        stage = new Stage(viewport, spriteBatch);
    }

    public void clear() {
        stage.clear();
    }

    public void add(final Actor actor) {
        stage.addActor(actor);
    }

    public void remove(final Actor actor) {
        List<Actor> actors = Lists.newLinkedList(stage.getActors());
        actors.remove(actor);
        stage.clear();
        actors.forEach(stage::addActor);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        graphicsService.getSpriteBatch().setProjectionMatrix(camera.combined);
        stage.draw();
    }

    @Override
    public void update(final long elapsedTime) {
        stage.act(TimeUtil.convertMsToFloatSeconds(elapsedTime));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
