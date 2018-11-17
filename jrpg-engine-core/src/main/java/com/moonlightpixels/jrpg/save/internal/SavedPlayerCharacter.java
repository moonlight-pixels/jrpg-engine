package com.moonlightpixels.jrpg.save.internal;

import lombok.Data;

@Data
public class SavedPlayerCharacter {
    private String key;
    private String name;
    private String animationSetId;
}
