package com.github.jaystgelais.jrpg.graphics;

import com.badlogic.gdx.utils.Disposable;

public interface Renderable extends Disposable {
    void render(GraphicsService graphicsService);
}
