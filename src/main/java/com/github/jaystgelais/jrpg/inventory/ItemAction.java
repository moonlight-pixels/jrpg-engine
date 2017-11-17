package com.github.jaystgelais.jrpg.inventory;

import com.github.jaystgelais.jrpg.party.PlayerCharacter;

public interface ItemAction {
    // TODO once we have enemies that can be targetted, this paramter should be a common interface.
    void perform(PlayerCharacter target);
}
