package com.moonlightpixels.jrpg.save.internal;

import lombok.Data;

import java.util.List;

@Data
public class SavedGameState {
    private String saveId;
    private List<SavedPlayerCharacter> cast;
    private List<String> roster;
    private List<SavedParty> parties;
    private int activePartyIndex;
}
