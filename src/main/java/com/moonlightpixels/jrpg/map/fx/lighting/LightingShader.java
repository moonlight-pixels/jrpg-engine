package com.moonlightpixels.jrpg.map.fx.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class LightingShader extends ShaderProgram {
    public LightingShader() {
        super(
                Gdx.files.internal("assets/jrpg/shaders/lighting/vertexShader.glsl"),
                Gdx.files.internal("assets/jrpg/shaders/lighting/pixelShader.glsl")
        );
    }
}
