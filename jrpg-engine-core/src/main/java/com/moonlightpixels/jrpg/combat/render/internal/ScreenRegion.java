package com.moonlightpixels.jrpg.combat.render.internal;

import lombok.Builder;
import lombok.Data;

@Data
public final class ScreenRegion {
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    @Builder
    public ScreenRegion(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getScreenX(final float internalX) {
        return internalX + x;
    }

    public float getScreenY(final float internalY) {
        return internalY + y;
    }
}
