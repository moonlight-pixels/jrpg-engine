package com.github.jaystgelais.jrpg.map.fx.lighting;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.state.Updatable;

public final class LightSource implements Updatable, Renderable {
    private static final long OSCILLATING_CYCLE_TIME_MS = 200L;
    private static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;
    private static final float OSCILLATING_VARIANCE = 0.2f;
    public static final String LIGHT_TEXTURE = "assets/jrpg/fx/lighting/light.png";

    private final int centerX;
    private final int centerY;
    private final int diameter;
    private final boolean isOscillating;
    private long timeInCycleMs = 0L;

    public LightSource(final int centerX, final int centerY, final int diameter, final boolean isOscillating) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.diameter = diameter;
        this.isOscillating = isOscillating;
    }

    public LightSource(final int centerX, final int centerY, final int diameter) {
        this(centerX, centerY, diameter, false);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        final int currentDiameter = getAdjustedDiameter();
        final OrthographicCamera camera = graphicsService.getCamera();
        Vector3 screenCoords = camera.project(
                new Vector3(centerX, centerY, 0), 0, 0, camera.viewportWidth, camera.viewportHeight
        );
        graphicsService.getSpriteBatch().draw(
                getLightTexture(graphicsService.getAssetManager()),
                Math.round(centerX - (currentDiameter / 2.0f)),
                Math.round(centerY - (currentDiameter / 2.0f)),
                currentDiameter, currentDiameter
        );
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(final long elapsedTime) {
        timeInCycleMs = (timeInCycleMs + elapsedTime) % OSCILLATING_CYCLE_TIME_MS;
    }

    private int getAdjustedDiameter() {
        if (isOscillating) {
            float zAngle = ((float) timeInCycleMs / (float) OSCILLATING_CYCLE_TIME_MS) * PI2;
            return Math.round(
                    (diameter * (1.0f - OSCILLATING_VARIANCE))
                            + ((diameter * OSCILLATING_VARIANCE) * (float) Math.sin(zAngle))
            );
        }

        return diameter;
    }

    static Texture getLightTexture(final AssetManager assetManager) {
        if (!assetManager.isLoaded(LIGHT_TEXTURE, Texture.class)) {
            assetManager.load(LIGHT_TEXTURE, Texture.class);
            assetManager.finishLoading();
        }
        return assetManager.get(LIGHT_TEXTURE, Texture.class);
    }
}
