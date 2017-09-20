package com.github.jaystgelais.jrpg.map.fx.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.fx.MapEffect;

import java.util.LinkedList;
import java.util.List;

public final class LightingEffect implements MapEffect {
    public static final Vector3 DARK_AMBIENT_COLOR = new Vector3(0.3f, 0.3f, 0.7f);
    public static final Vector3 DIM_AMBIENT_COLOR = new Vector3(0.7f, 0.7f, 1.0f);

    private static final float DEFAULT_AMBIENT_INTENSITY = 0.7f;

    private final Vector3 ambientColor;
    private final List<LightSource> lightSources = new LinkedList<>();
    private final LightingShader lightingShader = new LightingShader();
    private final DefaultShader defaultShader = new DefaultShader();
    private final float ambientIntensity;
    private FrameBuffer frameBuffer;

    public LightingEffect(final Vector3 ambientColor, final float ambientIntensity) {
        this.ambientColor = ambientColor;
        this.ambientIntensity = ambientIntensity;
    }

    public LightingEffect(final Vector3 ambientColor) {
        this(ambientColor, DEFAULT_AMBIENT_INTENSITY);
    }

    public void addLightSource(final LightSource lightSource) {
        lightSources.add(lightSource);
    }

    @Override
    public String getId() {
        return "jrpg-lighting";
    }

    @Override
    public void init(final GraphicsService graphicsService, final GameMap map) {
        lightingShader.begin();
        lightingShader.setUniformf(
                "resolution",
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        lightingShader.setUniformi("u_lightmap", 1);
        lightingShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y, ambientColor.z, ambientIntensity);
        lightingShader.end();
        frameBuffer = new FrameBuffer(
                Pixmap.Format.RGBA8888,
                graphicsService.getResolutionWidth(),
                graphicsService.getResolutionHeight(),
                false
        );
    }

    @Override
    public void preRender(final GraphicsService graphicsService) {
        final SpriteBatch batch = graphicsService.getSpriteBatch();
        final OrthographicCamera camera = graphicsService.getCamera();

        // remember SpriteBatch's current functions
        final int srcFunc = batch.getBlendSrcFunc();
        final int dstFunc = batch.getBlendDstFunc();

        frameBuffer.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(defaultShader);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.enableBlending();
        batch.begin();
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        lightSources.forEach(lightSource -> lightSource.render(graphicsService));
        batch.end();
        frameBuffer.end();

        batch.setShader(lightingShader);
        batch.begin();
        batch.setBlendFunction(srcFunc, dstFunc);
        batch.end();
    }

    @Override
    public void preMapRender(final GraphicsService graphicsService) {
        frameBuffer.getColorBufferTexture().bind(1);
        LightSource.getLightTexture(graphicsService.getAssetManager()).bind(0);
    }

    @Override
    public void postMapRender(final GraphicsService graphicsService) {

    }

    @Override
    public void postRender(final GraphicsService graphicsService) {
        graphicsService.getSpriteBatch().setShader(defaultShader);
    }

    @Override
    public void dispose() {
        lightingShader.dispose();
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
    }

    @Override
    public void update(final long elapsedTime) {
        lightSources.forEach(lightSource -> lightSource.update(elapsedTime));
    }
}
