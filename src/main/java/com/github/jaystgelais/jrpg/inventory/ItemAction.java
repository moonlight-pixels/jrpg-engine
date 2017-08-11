package com.github.jaystgelais.jrpg.inventory;

import com.github.jaystgelais.jrpg.party.Character;

public interface ItemAction {
    // TODO once we have enemies that can be targetted, this paramter should be a common interface.
    void perform(Character target);
}
