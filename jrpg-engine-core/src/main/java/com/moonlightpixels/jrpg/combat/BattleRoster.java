package com.moonlightpixels.jrpg.combat;

import lombok.Data;

import java.util.List;

@Data
public final class BattleRoster {
    private final List<Combatant> party;
    private final List<Combatant> enemies;
}
