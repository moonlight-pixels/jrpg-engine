package com.moonlightpixels.jrpg.internal.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class DefaultGraphicsContext implements GraphicsContext {
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final boolean centerCamera;

    public DefaultGraphicsContext(final OrthographicCamera camera,
                                  final SpriteBatch spriteBatch,
                                  final boolean centerCamera) {
        this.camera = camera;
        viewport = new FitViewport(
            camera.viewportWidth,
            camera.viewportHeight,
            camera
        );
        this.centerCamera = centerCamera;
        this.spriteBatch = spriteBatch;
        viewport.apply(centerCamera);
        camera.update();
    }

    @Override
    public void activate() {
        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);
    }

    @Override
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public Stage createStage() {
        return new Stage(viewport);
    }

    @Override
    public void centerOn(final float x, final float y) {
        camera.position.set(x, y, 0);
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, centerCamera);
        camera.update();
    }
}
