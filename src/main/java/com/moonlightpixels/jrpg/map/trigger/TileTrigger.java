package com.moonlightpixels.jrpg.map.trigger;

import com.moonlightpixels.jrpg.map.GameMap;
import com.moonlightpixels.jrpg.map.actor.Actor;

public interface TileTrigger {
    void onEnter(GameMap map, final Actor actor);
    void onExit(GameMap map, final Actor actor);
    void onInspect(GameMap map);
}
