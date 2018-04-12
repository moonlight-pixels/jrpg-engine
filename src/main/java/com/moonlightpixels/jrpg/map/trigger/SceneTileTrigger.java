package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.Game;
import com.moonlightpixels.jrpg.map.GameMap;
import com.moonlightpixels.jrpg.map.MapMode;
import com.moonlightpixels.jrpg.map.actor.Actor;
import com.moonlightpixels.jrpg.map.script.Scene;

public final class SceneTileTrigger implements TileTrigger {
    private final Scene scene;

    public SceneTileTrigger(final Scene scene) {
        this.scene = scene;
    }

    @Override
    public void onEnter(final GameMap map, final Actor actor) {
        ((MapMode) Game.getInstance().getActiveMode()).startScene(scene);
    }

    @Override
    public void onExit(final GameMap map, final Actor actor) {

    }

    @Override
    public void onInspect(final GameMap map) {

    }
}
