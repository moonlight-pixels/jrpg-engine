package com.github.jaystgelais.jrpg.map.actor;

import com.github.jaystgelais.jrpg.map.GameMap;

public abstract class ActorDefinition {
    public abstract Actor getActor(GameMap map);
}
