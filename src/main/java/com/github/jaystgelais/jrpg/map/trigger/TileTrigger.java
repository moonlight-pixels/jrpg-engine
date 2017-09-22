package com.github.jaystgelais.jrpg.map.trigger;

import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.actor.Actor;

public interface TileTrigger {
    void onEnter(GameMap map, final Actor actor);
    void onExit(GameMap map, final Actor actor);
    void onInspect(GameMap map);
}
