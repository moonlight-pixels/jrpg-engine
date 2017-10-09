package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.actor.ActionProvider;
import com.github.jaystgelais.jrpg.map.actor.Actor;
import com.github.jaystgelais.jrpg.map.actor.SceneController;

public abstract class AbstractActorCommand implements Command {
    private final GameMap map;
    private final String actorName;

    AbstractActorCommand(final GameMap map, final String actorName) {
        this.map = map;
        this.actorName = actorName;
    }

    protected abstract ActionProvider getActionProvider();

    @Override
    public final void start() {
        final SceneController sceneController = getActor().getSceneController();
        sceneController.setActionProvider(getActionProvider());
        startCommand();
    }

    protected void startCommand() { }

    protected final Actor getActor() {
        return map.getNamedActor(actorName);
    }
}
