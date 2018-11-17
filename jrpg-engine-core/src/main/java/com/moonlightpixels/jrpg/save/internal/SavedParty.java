package com.moonlightpixels.jrpg.save.internal;

import lombok.Data;

import java.util.List;

@Data
public class SavedParty {
    private int minimumSize;
    private int maximumSize;
    private List<String> members;
    private String mapId;
    private int x;
    private int y;
    private int mapLayer;
}
