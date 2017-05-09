package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.utils.Disposable;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;

import java.util.HashMap;
import java.util.Map;

public final class Layout extends AbstractContent {
    private final Map<String, Container> children = new HashMap<>();

    public Layout(final int width, final int height, final int screenPositionX, final int screenPositionY) {
        super(screenPositionY, screenPositionX, height, width);
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
        Container container = children.get(containerId);
        if (container == null) {
            throw new IllegalArgumentException("Unknown container: " + containerId);
        }
        createHorizontalDivisions(container, leftID, rightID, leftWidth);
        children.remove(containerId);
    }

    public void splitHorizontal(final String containerId, final String leftID, final String rightID,
                                final float leftPercent) {
        splitHorizontal(containerId, leftID, rightID, Math.round(getWidth() * leftPercent));
    }

    public void splitVertical(final String containerId, final String topID, final String bottomID,
                              final int topHeight) {
        Container container = children.get(containerId);
        if (container == null) {
            throw new IllegalArgumentException("Unknown container: " + containerId);
        }
        createVerticalDivisions(container, topID, bottomID, topHeight);
        children.remove(containerId);
    }

    public void splitVertical(final String containerId, final String topID, final String bottomID,
                              final float topPercent) {
        splitVertical(containerId, topID, bottomID, Math.round(getHeight() * topPercent));
    }

    private void createHorizontalDivisions(final Content parent, final String leftID, final String rightID,
                                           final int leftWidth) {
        children.put(
                leftID,
                new Container(
                        getContentPositionX(),
                        getContentPositionY(),
                        leftWidth,
                        getContentHeight()
                )
        );
        children.put(
                rightID,
                new Container(
                        getContentPositionX() + leftWidth,
                        getContentPositionY(),
                        getContentWidth() - leftWidth,
                        getContentHeight()
                )
        );
    }

    private void createVerticalDivisions(final Content parent, final String topID, final String bottomID,
                                         final int topHeight) {
        children.put(
                topID,
                new Container(
                        getContentPositionX(),
                        getContentPositionY() + getContentHeight() - topHeight,
                        getContentWidth(),
                        topHeight
                )
        );
        children.put(
                bottomID,
                new Container(
                        getContentPositionX(),
                        getContentPositionY(),
                        getContentWidth(),
                        getContentHeight() - topHeight
                )
        );
    }

    public Container getContainer(final String id) {
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
}
