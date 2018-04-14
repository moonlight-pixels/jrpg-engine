package com.moonlightpixels.jrpg.legacy.combat.svb;

import com.moonlightpixels.jrpg.legacy.combat.action.Targetable;
import com.moonlightpixels.jrpg.legacy.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.legacy.party.PlayerCharacter;
import com.moonlightpixels.jrpg.legacy.ui.dialog.Dialog;

public interface TargetableChoiceProviderDialogMapper {
    <T extends Targetable> Dialog<T> getDialog(TargetableChoiceProvider<T> provider, PlayerCharacter playerCharacter);
}
