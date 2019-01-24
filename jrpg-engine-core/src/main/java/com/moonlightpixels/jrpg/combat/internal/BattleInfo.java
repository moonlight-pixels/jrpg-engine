package com.moonlightpixels.jrpg.combat.internal;

import com.moonlightpixels.jrpg.combat.Combatant;
import com.moonlightpixels.jrpg.combat.render.BattleBackground;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;
import java.util.Optional;

@Data
public final class BattleInfo {
    private final List<Combatant> party;
    private final List<Combatant> enemies;
    private final BattleBackground background;

    @Builder
    public BattleInfo(@NonNull @Singular("playerChracter") final List<Combatant> party,
                      @NonNull @Singular("enemy") final List<Combatant> enemies,
                      final BattleBackground background) {
        this.party = party;
        this.enemies = enemies;
        this.background = background;
    }

    public Optional<BattleBackground> getBackground() {
        return Optional.ofNullable(background);
    }
}
