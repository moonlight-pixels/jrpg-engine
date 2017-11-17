package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.party.PlayerCharacter;

public final class PlayerInputEvent extends CombatEvent {
    private boolean isComplete = false;

    protected PlayerInputEvent(final int ticksRemaining, final PlayerCharacter playerCharacter) {
        super(ticksRemaining, playerCharacter);
    }

    @Override
    public void start(final Battle battle) {

    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void update(final long elapsedTime) {

    }

    private PlayerCharacter getPlayerCharacter() {
        return (PlayerCharacter) getOwner().orElseThrow(IllegalStateException::new);
    }
}
