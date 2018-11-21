package com.moonlightpixels.jrpg.save.internal;

import com.moonlightpixels.jrpg.player.PlayerCharacter;
import lombok.Data;

import java.util.List;

@Data
public class SavedGameState {
    private String saveId;
    private List<SavedPlayerCharacter> cast;
    private List<PlayerCharacter.Key> roster;
    private List<SavedParty> parties;
    private int activePartyIndex;
}
