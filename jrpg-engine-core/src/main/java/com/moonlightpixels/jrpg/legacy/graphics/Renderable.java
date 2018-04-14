package com.moonlightpixels.jrpg.legacy.graphics;

import com.badlogic.gdx.utils.Disposable;

public interface Renderable extends Disposable {
    void render(GraphicsService graphicsService);
}
