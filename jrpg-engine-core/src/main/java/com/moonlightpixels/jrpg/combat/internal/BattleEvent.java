package com.moonlightpixels.jrpg.combat.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.combat.CombatActionInstance;
import com.moonlightpixels.jrpg.combat.Combatant;
import lombok.Builder;
import lombok.Data;

@Data
public final class BattleEvent implements Comparable<BattleEvent> {
    private final Type type;
    private final Combatant combatant;
    private int ticksRemaining;
    private final Object payload;

    @Builder
    private BattleEvent(final Type type,
                        final Combatant combatant,
                        final int ticks,
                        final Object payload) {
        Preconditions.checkArgument(type.isValidPayload(payload));
        this.type = type;
        this.combatant = combatant;
        ticksRemaining = ticks;
        this.payload = payload;
    }

    public void tick() {
        ticksRemaining--;
    }

    public boolean canStart() {
        return ticksRemaining <= 0;
    }

    @Override
    public int compareTo(final BattleEvent other) {
        return Integer.compare(ticksRemaining, other.ticksRemaining);
    }

    public enum Type {
        Decision(null),
        Action(CombatActionInstance.class);

        private final Class<?> payloadType;

        Type(final Class<?> payloadType) {
            this.payloadType = payloadType;
        }

        boolean isValidPayload(final Object payload) {
            return payloadType == null || payloadType.isInstance(payload);
        }
    }
}
