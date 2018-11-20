package com.moonlightpixels.jrpg.story.event.internal;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.moonlightpixels.jrpg.map.scene.Scene;

public final class StoryEventDispatcher {
    private final MessageDispatcher messageDispatcher = new MessageDispatcher();

    public void addHandler(final EventHandler<?> eventHandler) {
        eventHandler.register(messageDispatcher);
    }

    public void sceneComplete(final Scene.Key sceneKey) {
        messageDispatcher.dispatchMessage(StoryEventTypes.SCENE_COMPLETE, sceneKey);
    }
}
