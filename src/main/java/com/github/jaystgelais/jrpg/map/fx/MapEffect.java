package com.github.jaystgelais.jrpg.map.fx;

import com.badlogic.gdx.utils.Disposable;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.map.GameMap;

public interface MapEffect extends Disposable {
    void init(GraphicsService graphicsService, GameMap map);
    void preRender(GraphicsService graphicsService);
    void preMapRender(GraphicsService graphicsService);
    void postMapRender(GraphicsService graphicsService);
    void postRender(GraphicsService graphicsService);
}
