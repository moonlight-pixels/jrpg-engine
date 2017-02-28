package com.github.jaystgelais.jrpg.graphics;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by jgelais on 2/19/17.
 */
public interface GraphicsService extends Disposable {
    LwjglApplicationConfiguration getConfiguration();

    void renderStart();

    void renderEnd();

    void init();

    void clearScreen();

    void resize(int width, int height);

    void drawSprite(Texture texture, float posX, float posY);

    void drawSprite(Pixmap pixmap, float posX, float posY);

    SpriteBatch getSpriteBatch();

    OrthographicCamera getCamera();

    BitmapFont getDefaultFont();

    int getResolutionWidth();

    void setResolutionWidth(int resolutionWidth);

    int getResolutionHeight();

    void setResolutionHeight(int resolutionHeight);
}
