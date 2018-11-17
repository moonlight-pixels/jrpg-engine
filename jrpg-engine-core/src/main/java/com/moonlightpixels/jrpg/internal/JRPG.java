package com.moonlightpixels.jrpg.internal;

public interface JRPG {
    void init();
    void update();
    void toMap();
    void toBattle();
    void toMainMenu();
    void exitBattle();
}
