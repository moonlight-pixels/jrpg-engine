package com.moonlightpixels.jrpg.save.internal;

import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.PlayerCharacter;
import lombok.Data;

@Data
public class SavedPlayerCharacter {
    private PlayerCharacter.Key key;
    private String name;
    private CharacterAnimationSet.Key animationSetKey;
}
