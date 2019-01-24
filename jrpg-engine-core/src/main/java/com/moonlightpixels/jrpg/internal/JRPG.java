package com.moonlightpixels.jrpg.internal;

import com.moonlightpixels.jrpg.combat.Encounter;

public interface JRPG {
    void init();
    void update();
    void toMap();
    void toBattle(Encounter encounter);
    void toMainMenu();
    void exitBattle();
}
