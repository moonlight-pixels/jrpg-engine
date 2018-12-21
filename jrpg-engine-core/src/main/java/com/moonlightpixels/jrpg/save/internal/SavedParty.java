package com.moonlightpixels.jrpg.save.internal;

import com.moonlightpixels.jrpg.map.MapDefinition;
import com.moonlightpixels.jrpg.player.internal.DefaultPlayerCharacter;
import lombok.Data;

import java.util.List;

@Data
public class SavedParty {
    private int minimumSize;
    private int maximumSize;
    private List<DefaultPlayerCharacter.Key> members;
    private MapDefinition.Key mapKey;
    private int x;
    private int y;
    private int mapLayer;
}
