package com.github.jaystgelais.jrpg.ui.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.ui.ScreenRegion;
import com.github.jaystgelais.jrpg.ui.UserInputHandler;

public abstract class Dialog<T> implements UserInputHandler {
    private final Listener<T> listener;
    private final ScreenRegion region;
    private WidgetGroup layout;
    private boolean complete = false;

    protected Dialog(final Listener<T> listener, final ScreenRegion region) {
        this.listener = listener;
        this.region = region;
    }

    protected abstract WidgetGroup createLayout();

    public final WidgetGroup getLayout() {
        if (layout == null) {
            layout = createLayout();
            layout.setX(region.getX());
            layout.setY(region.getY());
            layout.setWidth(region.getWidth());
            layout.setHeight(region.getHeight());
        }

        return layout;
    }

    @Override
    public final boolean isComplete() {
        return complete;
    }

    protected final void makeSelection(final T item) {
        listener.onSelect(item);
        Game.getInstance().getUserInterface().remove(getLayout());
        complete = true;
    }

    public interface Listener<T> {
        void onSelect(T choice);
    }
}
