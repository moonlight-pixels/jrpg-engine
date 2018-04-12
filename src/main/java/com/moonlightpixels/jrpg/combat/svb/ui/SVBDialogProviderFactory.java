package com.moonlightpixels.jrpg.combat.svb.ui;

import com.moonlightpixels.jrpg.party.PlayerCharacter;
import com.moonlightpixels.jrpg.ui.dialog.Dialog;
import com.moonlightpixels.jrpg.ui.dialog.DialogProvider;

public interface SVBDialogProviderFactory<T> {
    DialogProvider<T> getDialogProvider(PlayerCharacter playerCharacter, Dialog.Listener<T> listener);
}
