package com.moonlightpixels.jrpg.legacy.combat;

import com.moonlightpixels.jrpg.legacy.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.legacy.combat.action.Targetable;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.party.PlayerCharacter;

public interface PlayerInputHandler {
    void handlePlayerInput(PlayerCharacter playerCharacter, ActionTypeProvider provider);

    void handlePlayerInput(PlayerCharacter playerCharacter, TargetableChoiceProvider<? extends Targetable> provider);

    void handlePlayerInput(PlayerCharacter playerCharacter, TargetChoiceProvider provider,
                           AllowedTargets allowedTargets);
}
