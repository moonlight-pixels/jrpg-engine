package com.moonlightpixels.jrpg.legacy.map.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class ActorRenderFrame {
    private final TextureRegion sprite;
    private final int positionX;
    private final int positionY;

    public ActorRenderFrame(final TextureRegion sprite, final int positionX, final int positionY) {
        this.sprite = sprite;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public ActorRenderFrame translate(final ActorRenderTranslation translation) {
        return translation.translate(this);
    }
}
