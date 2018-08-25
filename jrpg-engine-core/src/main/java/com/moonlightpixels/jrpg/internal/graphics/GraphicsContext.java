package com.moonlightpixels.jrpg.internal.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface GraphicsContext {
    void activate();
    SpriteBatch getSpriteBatch();
    OrthographicCamera getCamera();
    Stage createStage();
    void centerOn(float x, float y);
    void resize(int width, int height);
}
