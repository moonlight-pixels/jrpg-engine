package com.moonlightpixels.jrpg.story.event.internal;

import com.moonlightpixels.jrpg.map.scene.Scene;
import com.moonlightpixels.jrpg.story.Task;

public final class SceneEventHandler extends EventHandler<Scene.Key> {
    private final Scene.Key sceneKey;

    public SceneEventHandler(final Task task, final Scene.Key sceneKey) {
        super(task);
        this.sceneKey = sceneKey;
    }

    @Override
    protected int getMessageCode() {
        return StoryEventTypes.SCENE_COMPLETE;
    }

    @Override
    protected boolean doesComplete(final Scene.Key event) {
        return event == sceneKey;
    }
}
