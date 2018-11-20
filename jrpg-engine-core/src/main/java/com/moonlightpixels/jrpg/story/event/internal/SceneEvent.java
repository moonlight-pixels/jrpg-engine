package com.moonlightpixels.jrpg.story.event.internal;

import com.moonlightpixels.jrpg.map.scene.Scene;
import lombok.Data;

@Data
public class SceneEvent {
    private final Scene.Key sceneKey;
}
