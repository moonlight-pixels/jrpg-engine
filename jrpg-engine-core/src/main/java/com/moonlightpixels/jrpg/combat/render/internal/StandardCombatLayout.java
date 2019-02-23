package com.moonlightpixels.jrpg.combat.render.internal;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;

public final class StandardCombatLayout implements CombatLayout {
    private static final float DEFAULT_UI_HEIGHT_PERCENT = 0.2f;
    private static final float DEFAULT_ENEMY_WIDTH_PERCENT = 0.75f;

    @Getter
    private final float screenWidth;
    @Getter
    private final float screenHeight;
    @Getter
    private ScreenRegion uiRegion;
    @Getter
    private ScreenRegion enemyRegion;
    @Getter
    private ScreenRegion playerRegion;

    public StandardCombatLayout(final float screenWidth, final float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        resize(DEFAULT_UI_HEIGHT_PERCENT, DEFAULT_ENEMY_WIDTH_PERCENT);
    }

    public StandardCombatLayout setUiHeightPercent(final float uiHeightPercent) {
        resize(uiHeightPercent, enemyRegion.getWidth() / screenWidth);
        return this;
    }

    public StandardCombatLayout setenemyWidthPercent(final float enemyWidthPercent) {
        resize(uiRegion.getHeight() / screenHeight, enemyWidthPercent);
        return this;
    }

    private void resize(final float uiHeightPercent, final float enemyWidthPercent) {
        uiRegion = ScreenRegion.builder()
            .x(0f)
            .y(0f)
            .width(screenWidth)
            .height(screenHeight * uiHeightPercent)
            .build();
        enemyRegion = ScreenRegion.builder()
            .x(0f)
            .y(screenHeight * uiHeightPercent)
            .width(screenWidth * uiHeightPercent)
            .height(screenHeight - screenHeight * uiHeightPercent)
            .build();
        playerRegion = ScreenRegion.builder()
            .x(screenWidth * enemyWidthPercent)
            .y(screenHeight * uiHeightPercent)
            .width(screenWidth - screenWidth * enemyWidthPercent)
            .height(screenHeight - screenHeight * uiHeightPercent)
            .build();
    }

    @Override
    public void placePlayerCharacters(final List<CombatActor> players) {
        final float maxActorWidth = players.stream()
            .map(CombatActor::getWidth)
            .max(Comparator.comparing(Float::valueOf))
            .orElseThrow(IllegalArgumentException::new);
        final float yShare = playerRegion.getHeight() / players.size();
        final float centerOnX = playerRegion.getScreenX(playerRegion.getWidth() - maxActorWidth);

        for (int index = 0; index < players.size(); index++) {
            final CombatActor player = players.get(index);
            final float centerOnY = playerRegion.getScreenY(
                playerRegion.getHeight() - ((index * yShare) + (yShare / 2))
            );
            player.setX(centerOnX - (player.getWidth() / 2));
            player.setY(centerOnY - (player.getHeight() / 2));
        }
    }
}
