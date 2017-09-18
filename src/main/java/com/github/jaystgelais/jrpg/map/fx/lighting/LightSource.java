package com.github.jaystgelais.jrpg.map.fx.lighting;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.graphics.Renderable;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.tween.IntegerTween;
import com.github.jaystgelais.jrpg.tween.SineTweenFunction;
import com.github.jaystgelais.jrpg.tween.Tween;

public final class LightSource implements Updatable, Renderable {
    private static final long OSCILLATING_CYCLE_TIME_MS = 1200;
    public static final String LIGHT_TEXTURE = "assets/jrpg/fx/lighting/light.png";

    private final int centerX;
    private final int centerY;
    private final int diameter;
    private final boolean isOscillating;
    private final Tween<Integer> diameterTween;

    public LightSource(final int centerX, final int centerY, final int diameter, final boolean isOscillating) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.diameter = diameter;
        this.isOscillating = isOscillating;
        this.diameterTween = new IntegerTween(
                new SineTweenFunction(), Math.round(diameter * 0.9f), diameter, OSCILLATING_CYCLE_TIME_MS, true
        );
        diameterTween.update(MathUtils.random(0L, OSCILLATING_CYCLE_TIME_MS));
    }

    public LightSource(final int centerX, final int centerY, final int diameter) {
        this(centerX, centerY, diameter, false);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        final int currentDiameter = getAdjustedDiameter();
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
        diameterTween.update(elapsedTime);
    }

    private int getAdjustedDiameter() {
        if (isOscillating) {
            return diameterTween.getValue() + Math.round(diameter * 0.04f* MathUtils.random());
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
