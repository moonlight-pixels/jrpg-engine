package com.moonlightpixels.jrpg.story.task;

import com.moonlightpixels.jrpg.map.scene.Scene;
import com.moonlightpixels.jrpg.story.Task;
import com.moonlightpixels.jrpg.story.event.internal.EventHandler;
import com.moonlightpixels.jrpg.story.event.internal.SceneEventHandler;

/**
 * SceneTasks complete when a given scene has been played.
 */
public final class SceneTask extends Task {
    private final Scene.Key sceneKey;

    /**
     * Creates a task associated with a specific Scene.
     *
     * @param key Key to uniquely identify this task
     * @param sceneKey Scene whose completion completes this task
     */
    public SceneTask(final Key key, final Scene.Key sceneKey) {
        super(key);
        this.sceneKey = sceneKey;
    }

    @Override
    protected EventHandler<?> getEventHandler() {
        return new SceneEventHandler(this, sceneKey);
    }
}
