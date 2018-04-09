package com.github.jaystgelais.jrpg.combat.svb;

import com.badlogic.gdx.graphics.Texture;

public interface SelectableActor {
    void showCursor(Texture cursor);
    void hideCursor();
}
