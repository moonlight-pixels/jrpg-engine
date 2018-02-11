package com.github.jaystgelais.jrpg.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.input.DelayedInput;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.Inputs;
import com.github.jaystgelais.jrpg.state.Updatable;

import java.util.Optional;

public final class MessagePopupPanel extends PopupPanel {
    private final String message;
    private final DelayedInput okInput = new DelayedInput(Inputs.OK);
    private boolean displayComplete = false;

    public MessagePopupPanel(final String message, final Skin skin, final float x, final float y, final float width,
                             final float height) {
        super(skin, x, y, width, height);
        this.message = message;
    }

    public MessagePopupPanel(final String message, final Skin skin) {
        super(skin);
        this.message = message;
    }

    public MessagePopupPanel(final String message) {
        this.message = message;
    }

    @Override
    protected Actor buildLayout(final Skin skin) {
        final Table layout = new Table();
        layout.setFillParent(true);
        final Label label = new Label(message, skin.get("conversation", Label.LabelStyle.class));
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
