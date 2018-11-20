package com.moonlightpixels.jrpg.map.scene;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Scene {
    private final Key key;

    public interface Key { }
}
