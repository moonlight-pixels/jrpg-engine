package com.moonlightpixels.jrpg.legacy.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.moonlightpixels.jrpg.legacy.input.DelayedInput;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.input.Inputs;
import com.moonlightpixels.jrpg.legacy.state.Updatable;

import java.util.Optional;

public final class MessagePopupPanel extends PopupPanel {
    private final String message;
    private final DelayedInput okInput = new DelayedInput(Inputs.OK);
    private boolean displayComplete = false;

    public MessagePopupPanel(final String message, final float x, final float y, final float width,
                             final float height) {
        super(x, y, width, height);
        this.message = message;
    }

    public MessagePopupPanel(final String message) {
        this.message = message;
    }

    @Override
    protected Actor buildLayout() {
        final Table layout = new Table();
        layout.setFillParent(true);
        final Label label = new Label(message, UiStyle.get("conversation", Label.LabelStyle.class));
        label.setWrap(true);
        label.setAlignment(Align.center);
        layout.add(label).fill().center();

        return layout;
    }

    @Override
    protected Optional<Updatable> getUpdatable() {
        return Optional.empty();
    }

    @Override
    protected Optional<InputHandler> getInputHandler() {
        return Optional.of(inputService -> {
            if (okInput.isPressed(inputService)) {
                displayComplete = true;
            }
        });
    }

    @Override
    protected boolean isDisplayComplete() {
        return displayComplete;
    }
}
