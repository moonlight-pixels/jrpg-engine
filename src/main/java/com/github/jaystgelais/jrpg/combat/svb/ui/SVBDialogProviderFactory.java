package com.github.jaystgelais.jrpg.combat.svb.ui;

import com.github.jaystgelais.jrpg.party.PlayerCharacter;
import com.github.jaystgelais.jrpg.ui.dialog.Dialog;
import com.github.jaystgelais.jrpg.ui.dialog.DialogProvider;

public interface SVBDialogProviderFactory<T> {
    DialogProvider<T> getDialogProvider(PlayerCharacter playerCharacter, Dialog.Listener<T> listener);
}
