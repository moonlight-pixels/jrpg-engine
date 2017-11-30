package com.github.jaystgelais.jrpg.combat.render;

import com.github.jaystgelais.jrpg.party.Party;
import com.github.jaystgelais.jrpg.ui.Container;
import com.github.jaystgelais.jrpg.ui.Layout;
import com.google.common.base.Preconditions;

public final class PlayerLayoutDefinition {
    public enum Orientation implements LayoutPartitioner {
        TopToBottom((layout, party) -> {
            final int slotCount = Math.max(1, party.getSize());
            final int slotHeight = layout.getHeight() / slotCount;
            layout.splitVertical(getPlayerLayoutId(0), "remaining", slotHeight);
            for (int i = 1; i < slotCount; i++) {
                layout.splitVertical("remaining", getPlayerLayoutId(i), "remaining", slotHeight);
            }
        }),
        LeftToRight((layout, party) -> {
            final int slotCount = Math.max(1, party.getSize());
            final int slotWidth = layout.getWidth() / slotCount;
            layout.splitHorizontal(getPlayerLayoutId(0), "remaining", slotWidth);
            for (int i = 1; i < slotCount; i++) {
                layout.splitHorizontal("remaining", getPlayerLayoutId(i), "remaining", slotWidth);
            }
        });

        private final LayoutPartitioner layoutPartitioner;

        Orientation(final LayoutPartitioner layoutPartitioner) {
            this.layoutPartitioner = layoutPartitioner;
        }


        @Override
        public void partitionLayout(final Layout layout, final Party party) {
            layoutPartitioner.partitionLayout(layout, party);
        }
    }

    static String getPlayerLayoutId(final int index) {
        return String.format("player:%d", index);
    }

    private final Orientation orientation;

    public PlayerLayoutDefinition(final Orientation orientation) {
        Preconditions.checkNotNull(orientation);
        this.orientation = orientation;
    }

    public Layout getLayout(final Container container, final Party party) {
        Preconditions.checkNotNull(container);
        Preconditions.checkNotNull(party);

        final Layout layout = new Layout(
                container.getContentPositionX(),
                container.getContentPositionY(),
                container.getContentWidth(),
                container.getContentHeight()
        );
        orientation.partitionLayout(layout, party);

        return layout;
    }

    private interface LayoutPartitioner {
        void partitionLayout(Layout layout, Party party);
    }
}
