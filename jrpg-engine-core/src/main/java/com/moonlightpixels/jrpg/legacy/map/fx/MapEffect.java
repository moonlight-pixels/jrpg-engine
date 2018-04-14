package com.moonlightpixels.jrpg.legacy.map.fx;

import com.badlogic.gdx.utils.Disposable;
import com.moonlightpixels.jrpg.legacy.graphics.GraphicsService;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface MapEffect extends Disposable, Updatable {
    String getId();
    void init(GraphicsService graphicsService, GameMap map);
    void preRender(GraphicsService graphicsService);
    void preMapRender(GraphicsService graphicsService);
    void postMapRender(GraphicsService graphicsService);
    void postRender(GraphicsService graphicsService);
}
