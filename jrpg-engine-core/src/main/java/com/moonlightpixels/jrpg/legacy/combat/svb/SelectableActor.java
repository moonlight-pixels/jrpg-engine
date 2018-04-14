package com.moonlightpixels.jrpg.legacy.combat.svb;

import com.badlogic.gdx.graphics.Texture;

public interface SelectableActor {
    void showCursor(Texture cursor);
    void hideCursor();
}
