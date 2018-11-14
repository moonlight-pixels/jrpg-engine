package com.moonlightpixels.jrpg.player;

import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import lombok.Data;

@Data
public final class PlayerCharacter {
    private final Key key;
    private String name;
    private CharacterAnimationSet animationSet;

    public interface Key { }
}
