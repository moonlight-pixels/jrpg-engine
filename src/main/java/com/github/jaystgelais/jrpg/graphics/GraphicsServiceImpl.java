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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.jaystgelais.jrpg.graphics.factory.SpriteBatchFactory;
import com.github.jaystgelais.jrpg.graphics.factory.SpriteBatchFactoryImpl;
import com.github.jaystgelais.jrpg.ui.text.FontDefinition;
import com.github.jaystgelais.jrpg.ui.text.FontSet;

import java.util.ArrayDeque;
import java.util.Deque;

public final class GraphicsServiceImpl implements GraphicsService {
    public static final int DEFAULT_RESOLUTION_WIDTH = 384;
    public static final int DEFAULT_RESOLUTION_HEIGHT = 216;
    public static final float DEFAULT_FONT_TARGET_LINES = 25f;

    private final AssetManager assetManager;
    private final SpriteBatchFactory spriteBatchFactory;
    private final Deque<Disposable> disposables = new ArrayDeque<>();
    private FontSet fontSet;
    private FontDefinition textFont;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int resolutionWidth = DEFAULT_RESOLUTION_WIDTH;
    private int resolutionHeight = DEFAULT_RESOLUTION_HEIGHT;

    public GraphicsServiceImpl(final AssetManager assetManager) {
        this(assetManager, new SpriteBatchFactoryImpl());
    }

    GraphicsServiceImpl(final AssetManager assetManager, final SpriteBatchFactory spriteBatchFactory) {
        this.assetManager = assetManager;
        this.spriteBatchFactory = spriteBatchFactory;
        camera = new OrthographicCamera(resolutionWidth, resolutionHeight);
    }

    @Override
    public LwjglApplicationConfiguration getConfiguration() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "my-gdx-game";
        cfg.useGL30 = false;
        cfg.width = resolutionWidth;
        cfg.height = resolutionHeight;
        cfg.forceExit = false;

        return cfg;
    }

    @Override
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void renderStart() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    @Override
    public void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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
        viewport = new FitViewport(resolutionWidth, resolutionHeight, camera);
        camera.update();
        spriteBatch = spriteBatchFactory.createSpriteBatch();

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
        viewport.update(width, height, true);
    }

    @Override
    public void drawSprite(final Texture texture, final float posX, final float posY) {
        spriteBatch.draw(texture, posX, posY);
    }

    @Override
    public void drawSprite(final TextureRegion textureRegion, final float posX, final float posY) {
        spriteBatch.draw(textureRegion, posX, posY);
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
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public int getCameraOffsetX() {
        return Math.round(getCamera().position.x - (getResolutionWidth() / 2f));
    }

    @Override
    public int getCameraOffsetY() {
        return Math.round(getCamera().position.y - (getResolutionHeight() / 2f));
    }

    @Override
    public TiledMapRenderer getTileMapRenderer(final TiledMap tiledMap) {
        return new OrthogonalTiledMapRenderer(tiledMap, spriteBatch);
    }

    @Override
    public FontSet getFontSet() {
        if (fontSet == null) {
            final FontSet.Builder builder = FontSet.newFontSet(this);
            if (textFont != null) {
                builder.setTextFont(textFont);
            }
            fontSet = builder.create();
        }
        return fontSet;
    }

    public FontDefinition getTextFont() {
        return textFont;
    }

    public void setTextFont(final FontDefinition textFont) {
        this.textFont = textFont;
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
