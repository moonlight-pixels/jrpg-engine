package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Select Lists are the backbone of menus. The present a list of rendered choices with associated actions.
 */
public final class SelectList extends WidgetGroup implements InputHandler {
    private static final List<InputScheme> SCHEMES_REQUIRING_CURSOR = Lists.newArrayList(
        InputScheme.Controller,
        InputScheme.Keyboard
    );

    private final List<Item> items = new LinkedList<>();
    private final SelectListStyle style;
    private final int columns;
    private InputScheme inputScheme;
    private int currentSelectionIndex = 0;
    private boolean active = true;
    private Table layout;

    /**
     * Creates a Select list form a List of Items with the given style and number of columns.
     *
     * @param items Items in the SelectList
     * @param style Component Style
     * @param columns Number of columns
     */
    public SelectList(final List<Item> items, final SelectListStyle style, final int columns) {
        Preconditions.checkArgument(!items.isEmpty());
        Preconditions.checkNotNull(style);
        Preconditions.checkArgument(columns > 0);
        this.items.addAll(items);
        this.style = style;
        this.columns = columns;
        setTransform(false);
    }

    /**
     * Creates a single column SelectList.
     *
     * @param items Items in the SelectList
     * @param style Component Style
     */
    public SelectList(final List<Item> items, final SelectListStyle style) {
        this(items, style, 1);
    }

    @Override
    public boolean handleControlEvent(final ControlEvent event) {
        switch (event) {
            case ACTION_PRESSED:
                items.get(currentSelectionIndex).getAction().perform();
                break;
            case UP_PRESSED:
                currentSelectionIndex = Math.max(currentSelectionIndex - columns, 0);
                onSelect(items.get(currentSelectionIndex));
                break;
            case DOWN_PRESSED:
                currentSelectionIndex = Math.min(currentSelectionIndex + columns, items.size() - 1);
                onSelect(items.get(currentSelectionIndex));
                break;
            case RIGHT_PRESSED:
                currentSelectionIndex = Math.min(currentSelectionIndex + 1, items.size() - 1);
                onSelect(items.get(currentSelectionIndex));
                break;
            case LEFT_PRESSED:
                currentSelectionIndex = Math.max(currentSelectionIndex - 1, 0);
                onSelect(items.get(currentSelectionIndex));
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        Optional<Item> clickedItem = items.stream()
            .filter(item -> event.appliesTo(item.getDisplay()))
            .findFirst();

        if (clickedItem.isPresent()) {
            clickedItem.get().getAction().perform();
            return true;
        }

        return false;
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) {
        this.inputScheme = inputScheme;
    }

    @Override
    public void layout() {
        final int totalRows = ((items.size() - 1) / columns)  + 1;

        layout = new Table();
        layout.setFillParent(true);
        layout.defaults().space(style.getCursor().getWidth() / 2f);
        addActor(layout);

        for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                final int itemIndex = (rowIndex * columns) + colIndex;
                final Item item = items.get(itemIndex);
                final boolean isSelected = (currentSelectionIndex == itemIndex);

                if (isCursorUsed() && isActive() && isSelected) {
                    final Image cursor = new Image(style.getCursor());
                    layout.add(cursor).padLeft(style.getCursor().getWidth());
                }

                layout.add(wrapDisplay(item.getDisplay())).fillX().expand();
            }
            layout.row();
        }
    }

    private boolean isCursorUsed() {
        return SCHEMES_REQUIRING_CURSOR.contains(inputScheme);
    }

    private <T extends Actor> Container<T> wrapDisplay(final T display) {
        final Container<T> container = new Container<>(display);
        container.align(Align.left);
        return container;
    }

    /**
     * Whether or not this SelectList is active.
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set this SelectList active.
     */
    public void activate() {
        active = true;
        invalidate();
    }

    /**
     * Set this SelectList inactive.
     */
    public void deactivate() {
        active = false;
        invalidate();
    }

    @Override
    public float getPrefWidth() {
        return (layout != null) ? layout.getPrefWidth() : 0;
    }

    @Override
    public float getPrefHeight() {
        return (layout != null) ? layout.getPrefHeight() : 0;
    }

    private void onSelect(final Item item) {
        item.getOnCursorAction().ifPresent(Action::perform);
        clear();
        layout();
    }

    /**
     * An item in the select list, responsible for providing a renderable Actor and action if selected.
     */
    public static final class Item {
        private final Actor display;
        private final Action action;
        private final Action onCursorAction;

        /**
         * Creates an item from its renderable Actor and actions for onSelect and onCursor events.
         *
         * @param display Actor to draw for this Item
         * @param action Action executed when Item is selected
         * @param onCursorAction Action executed when cursor (in Controller/Keyboard schemes) is placed on Item
         */
        public Item(final Actor display, final Action action, final Action onCursorAction) {
            this.display = display;
            this.action = action;
            this.onCursorAction = onCursorAction;
        }

        /**
         * Creates an Item without an onCursor action.
         *
         * @param display Actor to draw for this Item
         * @param action Action executed when Item is selected
         */
        public Item(final Actor display, final Action action) {
            this(display, action, null);
        }

        /**
         * Get Actor to draw for this Item's display.
         *
         * @return Actor to draw
         */
        public Actor getDisplay() {
            return display;
        }

        /**
         * Get Action executed when Item is selected.
         *
         * @return onSelect Action
         */
        public Action getAction() {
            return action;
        }

        /**
         * Optionally get Action to execute when cursor (in Controller/Keyboard schemes) is placed on Item.
         *
         * @return onCursor Action
         */
        public Optional<Action> getOnCursorAction() {
            return Optional.ofNullable(onCursorAction);
        }
    }

    /**
     * Functional interface representing an action taken in respose to user input.
     */
    @FunctionalInterface
    public interface Action {
        /**
         * Perform this Action.
         */
        void perform();
    }

    /**
     * Style data for SelectLists.
     */
    public static final class SelectListStyle {
        private final Texture cursor;

        /**
         * Creates a SelectListStyle with the given cursor Texture.
         *
         * @param cursor Texture for cursor
         */
        public SelectListStyle(final Texture cursor) {
            Preconditions.checkNotNull(cursor);
            this.cursor = cursor;
        }

        /**
         * Creates a new SelectListStyle, copying style from another SelectListStyle instance.
         *
         * @param style Instance to copy.
         */
        public SelectListStyle(final SelectListStyle style) {
            Preconditions.checkNotNull(style);
            this.cursor = style.getCursor();
        }

        /**
         * Returns Texture for cursor.
         *
         * @return cursor Texture
         */
        public Texture getCursor() {
            return cursor;
        }
    }
}
