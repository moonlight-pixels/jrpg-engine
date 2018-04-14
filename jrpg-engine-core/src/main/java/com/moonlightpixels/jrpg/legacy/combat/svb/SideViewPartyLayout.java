package com.moonlightpixels.jrpg.legacy.combat.svb;

import com.moonlightpixels.jrpg.legacy.combat.PartyLayout;
import com.moonlightpixels.jrpg.legacy.graphics.Coordinate2D;
import com.moonlightpixels.jrpg.legacy.party.Party;
import com.moonlightpixels.jrpg.legacy.party.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public final class SideViewPartyLayout implements PartyLayout {
    private final int frontRowX;
    private final int actionX;
    private final int rowSpacing;
    private final int topEdgeY;
    private final int bottomEdgeY;

    private SideViewPartyLayout(final int frontRowX, final int actionX, final int rowSpacing,
                                final int topEdgeY, final int bottomEdgeY) {
        this.frontRowX = frontRowX;
        this.actionX = actionX;
        this.rowSpacing = rowSpacing;
        this.topEdgeY = topEdgeY;
        this.bottomEdgeY = bottomEdgeY;
    }

    @Override
    public Map<PlayerCharacter, Coordinate2D> getPlayerPositions(final Party party) {
        final int spacePerPlayer = (topEdgeY - bottomEdgeY) / party.getSize();
        final Map<PlayerCharacter, Coordinate2D> playerPositions = new HashMap<>();

        for (int index = 0; index < party.getSize(); index++) {
            final PlayerCharacter player = party.getMembers().get(party.getSize() - index - 1);
            playerPositions.put(
                    player,
                    new Coordinate2D(
                            (player.isBackrow()) ? frontRowX + rowSpacing : frontRowX,
                            bottomEdgeY + (index * spacePerPlayer)
                    )
            );
        }

        return playerPositions;
    }

    public int getFrontRowX() {
        return frontRowX;
    }

    public int getActionX() {
        return actionX;
    }

    public int getRowSpacing() {
        return rowSpacing;
    }

    public int getTopEdgeY() {
        return topEdgeY;
    }

    public int getBottomEdgeY() {
        return bottomEdgeY;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int frontRowX;
        private int actionX;
        private int rowSpacing;
        private int topEdgeY;
        private int bottomEdgeY;

        private Builder() { }

        public Builder setFrontRowX(final int frontRowX) {
            this.frontRowX = frontRowX;
            return this;
        }

        public Builder setActionX(final int actionX) {
            this.frontRowX = actionX;
            return this;
        }

        public Builder setRowSpacing(final int rowSpacing) {
            this.rowSpacing = rowSpacing;
            return this;
        }

        public Builder setTopEdgeY(final int topEdgeY) {
            this.topEdgeY = topEdgeY;
            return this;
        }

        public Builder setBottomEdgeY(final int bottomEdgeY) {
            this.bottomEdgeY = bottomEdgeY;
            return this;
        }

        public SideViewPartyLayout build() {
            return new SideViewPartyLayout(frontRowX, actionX, rowSpacing, topEdgeY, bottomEdgeY);
        }
    }
}
