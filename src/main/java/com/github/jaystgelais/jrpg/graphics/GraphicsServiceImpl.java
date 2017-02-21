package com.github.jaystgelais.jrpg.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayDeque;
import java.util.Deque;

public final class GraphicsServiceImpl implements GraphicsService {
    public static final int DEFAULT_RESOLUTION_WIDTH = 480;
    public static final int DEFAULT_RESOLUTION_HEIGHT = 320;
    public static final float DEFAULT_FONT_TARGET_LINES = 25f;

    private final AssetManager assetManager;
    private final Deque<Disposable> disposables = new ArrayDeque<>();
    private BitmapFont defaultFont;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private int resolutionWidth = DEFAULT_RESOLUTION_WIDTH;
    private int resolutionHeight = DEFAULT_RESOLUTION_HEIGHT;

    public GraphicsServiceImpl(final AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public LwjglApplicationConfiguration getConfiguration() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "my-gdx-game";
        cfg.useGL30 = false;
        cfg.width = resolutionWidth;
        cfg.height = resolutionHeight;

        return cfg;
    }

    @Override
    public void renderStart() {
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
    }

    @Override
    public void renderEnd() {
        spriteBatch.end();
        while (!disposables.isEmpty()) {
            disposables.pop().dispose();
        }
    }

    @Override
    public void init() {
        camera = new OrthographicCamera(resolutionWidth, resolutionHeight);
        camera.position.set(resolutionWidth / 2f, resolutionHeight / 2f, 0);
        camera.update();
        spriteBatch = new SpriteBatch();

        defaultFont = new BitmapFont();
        defaultFont.getData().setScale(calculateDefaultFontScale(defaultFont));
        defaultFont.getData().markupEnabled = true;

        Graphics.Monitor monitor = Gdx.graphics.getMonitor();
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(monitor);
        Gdx.graphics.setFullscreenMode(displayMode);
    }

    private float calculateDefaultFontScale(final BitmapFont font) {
        return getResolutionHeight() / (DEFAULT_FONT_TARGET_LINES * font.getLineHeight());
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void drawSprite(final Texture texture, final float posX, final float posY) {
        spriteBatch.draw(texture, posX, posY);
    }

    @Override
    public void drawSprite(final Pixmap pixmap, final float posX, final float posY) {
        Texture texture = new Texture(pixmap);
        disposables.push(texture);
        drawSprite(texture, posX, posY);
    }

    @Override
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public BitmapFont getDefaultFont() {
        return defaultFont;
    }

    @Override
    public int getResolutionWidth() {
        return resolutionWidth;
    }

    @Override
    public void setResolutionWidth(final int resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    @Override
    public int getResolutionHeight() {
        return resolutionHeight;
    }

    @Override
    public void setResolutionHeight(final int resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }
}
