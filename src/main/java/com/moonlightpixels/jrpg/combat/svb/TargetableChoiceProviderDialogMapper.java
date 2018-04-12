package com.moonlightpixels.jrpg.combat.svb;

import com.moonlightpixels.jrpg.combat.action.Targetable;
import com.moonlightpixels.jrpg.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.party.PlayerCharacter;
import com.moonlightpixels.jrpg.ui.dialog.Dialog;

public interface TargetableChoiceProviderDialogMapper {
    <T extends Targetable> Dialog<T> getDialog(TargetableChoiceProvider<T> provider, PlayerCharacter playerCharacter);
}
