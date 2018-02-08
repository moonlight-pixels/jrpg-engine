package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.utils.Disposable;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;

import java.util.HashMap;
import java.util.Map;

public final class Layout extends AbstractContent {
    private final Map<String, LegacyContainer> children = new HashMap<>();

    public Layout(final int screenPositionX, final int screenPositionY, final int width, final int height) {
        super(screenPositionX, screenPositionY, width, height);
    }

    @Override
    protected boolean canChangeMargins() {
        return children.isEmpty();
    }

    public void splitHorizontal(final String leftID, final String rightID, final int leftWidth) {
        if (!children.isEmpty()) {
            throw new IllegalStateException("Layout has already been split. You must specify section to split.");
        }
        createHorizontalDivisions(this, leftID, rightID, leftWidth);
    }

    public void splitHorizontal(final String leftID, final String rightID, final float leftPercent) {
        splitHorizontal(leftID, rightID, Math.round(getWidth() * leftPercent));
    }

    public void splitVertical(final String topID, final String bottomID, final int topHeight) {
        if (!children.isEmpty()) {
            throw new IllegalStateException("Layout has already been split. You must specify section to split.");
        }
        createVerticalDivisions(this, topID, bottomID, topHeight);
    }

    public void splitVertical(final String topID, final String bottomID, final float topPercent) {
        splitVertical(topID, bottomID, Math.round(getHeight() * topPercent));
    }

    public void splitHorizontal(final String containerId, final String leftID, final String rightID,
                                final int leftWidth) {
        LegacyContainer legacyContainer = children.get(containerId);
        if (legacyContainer == null) {
            throw new IllegalArgumentException("Unknown container: " + containerId);
        }
        children.remove(containerId);
        createHorizontalDivisions(legacyContainer, leftID, rightID, leftWidth);
    }

    public void splitHorizontal(final String containerId, final String leftID, final String rightID,
                                final float leftPercent) {
        LegacyContainer legacyContainer = children.get(containerId);
        if (legacyContainer == null) {
            throw new IllegalArgumentException("Unknown container: " + containerId);
        }
        splitHorizontal(containerId, leftID, rightID, Math.round(legacyContainer.getWidth() * leftPercent));
    }

    public void splitVertical(final String containerId, final String topID, final String bottomID,
                              final int topHeight) {
        LegacyContainer legacyContainer = children.get(containerId);
        if (legacyContainer == null) {
            throw new IllegalArgumentException("Unknown container: " + containerId);
        }
        children.remove(containerId);
        createVerticalDivisions(legacyContainer, topID, bottomID, topHeight);
    }

    public void splitVertical(final String containerId, final String topID, final String bottomID,
                              final float topPercent) {
        LegacyContainer legacyContainer = children.get(containerId);
        if (legacyContainer == null) {
            throw new IllegalArgumentException("Unknown container: " + containerId);
        }
        splitVertical(containerId, topID, bottomID, Math.round(legacyContainer.getHeight() * topPercent));
    }

    private void createHorizontalDivisions(final Content parent, final String leftID, final String rightID,
                                           final int leftWidth) {
        children.put(
                leftID,
                new LegacyContainer(
                        parent.getScreenPositionX(),
                        parent.getScreenPositionY(),
                        leftWidth,
                        parent.getHeight()
                )
        );
        children.put(
                rightID,
                new LegacyContainer(
                        parent.getScreenPositionX() + leftWidth,
                        parent.getScreenPositionY(),
                        parent.getWidth() - leftWidth,
                        parent.getHeight()
                )
        );
    }

    private void createVerticalDivisions(final Content parent, final String topID, final String bottomID,
                                         final int topHeight) {
        children.put(
                topID,
                new LegacyContainer(
                        parent.getScreenPositionX(),
                        parent.getScreenPositionY() + parent.getHeight() - topHeight,
                        parent.getWidth(),
                        topHeight
                )
        );
        children.put(
                bottomID,
                new LegacyContainer(
                        parent.getScreenPositionX(),
                        parent.getScreenPositionY(),
                        parent.getWidth(),
                        parent.getHeight() - topHeight
                )
        );
    }

    public LegacyContainer getContainer(final String id) {
        return children.get(id);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        children.values().forEach(container -> container.render(graphicsService));
    }

    @Override
    public void dispose() {
        children.values().forEach(Disposable::dispose);
    }

    @Override
    public void update(final long elapsedTime) {
        children.values().forEach(container -> container.update(elapsedTime));
    }

    @Override
    public void handleInput(final InputService inputService) {

    }
}
