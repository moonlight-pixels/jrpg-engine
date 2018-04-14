package com.moonlightpixels.jrpg.legacy.combat.svb.ui;

import com.moonlightpixels.jrpg.legacy.party.PlayerCharacter;
import com.moonlightpixels.jrpg.legacy.ui.dialog.Dialog;
import com.moonlightpixels.jrpg.legacy.ui.dialog.DialogProvider;

public interface SVBDialogProviderFactory<T> {
    DialogProvider<T> getDialogProvider(PlayerCharacter playerCharacter, Dialog.Listener<T> listener);
}
