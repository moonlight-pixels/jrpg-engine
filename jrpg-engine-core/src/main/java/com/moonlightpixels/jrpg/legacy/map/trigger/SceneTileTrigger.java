package com.moonlightpixels.jrpg.legacy.map.trigger;

import com.moonlightpixels.jrpg.legacy.Game;
import com.moonlightpixels.jrpg.legacy.map.MapMode;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.actor.Actor;
import com.moonlightpixels.jrpg.legacy.map.script.Scene;

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
