package com.github.jaystgelais.jrpg.combat.svb;

import com.github.jaystgelais.jrpg.combat.action.Targetable;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;
import com.github.jaystgelais.jrpg.party.PlayerCharacter;
import com.github.jaystgelais.jrpg.ui.dialog.Dialog;

public interface TargetableChoiceProviderDialogMapper {
    <T extends Targetable> Dialog<T> getDialog(TargetableChoiceProvider<T> provider, PlayerCharacter playerCharacter);
}
