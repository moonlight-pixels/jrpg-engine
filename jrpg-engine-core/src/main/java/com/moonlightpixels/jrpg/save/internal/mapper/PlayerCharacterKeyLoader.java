package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;
import com.moonlightpixels.jrpg.player.PlayerCharacter;

public interface PlayerCharacterKeyLoader {
    PlayerCharacter.Key load(String savedValue) throws SavedStateLoadExcpetion;
}
