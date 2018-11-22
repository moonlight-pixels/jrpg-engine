package com.moonlightpixels.jrpg.player;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PlayerCharacter {
    private final Key key;
    private String name;
    private CharacterAnimationSet animationSet;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public interface Key { }
}
