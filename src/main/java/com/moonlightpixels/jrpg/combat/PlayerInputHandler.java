package com.moonlightpixels.jrpg.combat;

import com.moonlightpixels.jrpg.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.combat.action.Targetable;
import com.moonlightpixels.jrpg.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.party.PlayerCharacter;

public interface PlayerInputHandler {
    void handlePlayerInput(PlayerCharacter playerCharacter, ActionTypeProvider provider);

    void handlePlayerInput(PlayerCharacter playerCharacter, TargetableChoiceProvider<? extends Targetable> provider);

    void handlePlayerInput(PlayerCharacter playerCharacter, TargetChoiceProvider provider,
                           AllowedTargets allowedTargets);
}
