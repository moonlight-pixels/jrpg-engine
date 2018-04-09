package com.github.jaystgelais.jrpg.combat;

import com.github.jaystgelais.jrpg.combat.action.ActionTypeProvider;
import com.github.jaystgelais.jrpg.combat.action.AllowedTargets;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.combat.action.Targetable;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;
import com.github.jaystgelais.jrpg.party.PlayerCharacter;

public interface PlayerInputHandler {
    void handlePlayerInput(PlayerCharacter playerCharacter, ActionTypeProvider provider);

    void handlePlayerInput(PlayerCharacter playerCharacter, TargetableChoiceProvider<? extends Targetable> provider);

    void handlePlayerInput(PlayerCharacter playerCharacter, TargetChoiceProvider provider,
                           AllowedTargets allowedTargets);
}
