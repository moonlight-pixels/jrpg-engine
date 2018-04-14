package com.moonlightpixels.jrpg.legacy.map.trigger;

import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.actor.Actor;

public interface TileTrigger {
    void onEnter(GameMap map, final Actor actor);
    void onExit(GameMap map, final Actor actor);
    void onInspect(GameMap map);
}
