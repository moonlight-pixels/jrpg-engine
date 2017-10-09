package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.MapMode;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.script.Scene;

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
