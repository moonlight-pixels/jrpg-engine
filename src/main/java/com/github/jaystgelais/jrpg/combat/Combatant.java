package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.stats.StatHolder;

public interface Combatant extends StatHolder {
    String getName();
    void applyDamage(final int damage);
    int getLevel();
}
