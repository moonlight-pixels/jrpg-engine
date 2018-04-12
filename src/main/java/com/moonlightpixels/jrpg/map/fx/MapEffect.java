package com.moonlightpixels.jrpg.map.fx;

import com.badlogic.gdx.utils.Disposable;
import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.map.GameMap;
import com.moonlightpixels.jrpg.state.Updatable;

public interface MapEffect extends Disposable, Updatable {
    String getId();
    void init(GraphicsService graphicsService, GameMap map);
    void preRender(GraphicsService graphicsService);
    void preMapRender(GraphicsService graphicsService);
    void postMapRender(GraphicsService graphicsService);
    void postRender(GraphicsService graphicsService);
}
