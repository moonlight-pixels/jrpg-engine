package com.moonlightpixels.jrpg.legacy.map.fx.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DefaultShader extends ShaderProgram {
    public DefaultShader() {
        super(
                Gdx.files.internal("assets/jrpg/shaders/lighting/vertexShader.glsl"),
                Gdx.files.internal("assets/jrpg/shaders/lighting/defaultPixelShader.glsl")
        );
    }
}
