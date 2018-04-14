package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.map.actor.ActionProvider;
import com.moonlightpixels.jrpg.legacy.map.GameMap;
import com.moonlightpixels.jrpg.legacy.map.actor.Actor;
import com.moonlightpixels.jrpg.legacy.map.actor.SceneController;

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
