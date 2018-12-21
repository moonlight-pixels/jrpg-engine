package com.moonlightpixels.jrpg.save.internal;

import com.moonlightpixels.jrpg.combat.stats.Stat;
import com.moonlightpixels.jrpg.map.character.CharacterAnimationSet;
import com.moonlightpixels.jrpg.player.internal.DefaultPlayerCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
public class SavedPlayerCharacter {
    private DefaultPlayerCharacter.Key key;
    private String name;
    private CharacterAnimationSet.Key animationSetKey;
    private final List<StatValue> stats = new LinkedList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatValue {
        private Stat.Key key;
        private int value;
    }
}
