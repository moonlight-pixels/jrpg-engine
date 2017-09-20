package com.github.jaystgelais.jrpg.map.fx;

import com.badlogic.gdx.utils.Disposable;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.state.Updatable;

public interface MapEffect extends Disposable, Updatable {
    String getId();
    void init(GraphicsService graphicsService, GameMap map);
    void preRender(GraphicsService graphicsService);
    void preMapRender(GraphicsService graphicsService);
    void postMapRender(GraphicsService graphicsService);
    void postRender(GraphicsService graphicsService);
}
