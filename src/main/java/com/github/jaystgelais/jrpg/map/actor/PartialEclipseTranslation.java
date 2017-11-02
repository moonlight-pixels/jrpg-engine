package com.github.jaystgelais.jrpg.map.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class PartialEclipseTranslation implements ActorRenderTranslation {
    private final int eclipsedHeight;

    public PartialEclipseTranslation(final int eclipsedHeight) {
        this.eclipsedHeight = eclipsedHeight;
    }

    @Override
    public ActorRenderFrame translate(final ActorRenderFrame input) {
        final TextureRegion sprite = new TextureRegion(input.getSprite());
        sprite.setRegionHeight(input.getSprite().getRegionHeight() - eclipsedHeight);

        return new ActorRenderFrame(
                sprite,
                input.getPositionX(),
                input.getPositionY() + eclipsedHeight
        );
    }
}
