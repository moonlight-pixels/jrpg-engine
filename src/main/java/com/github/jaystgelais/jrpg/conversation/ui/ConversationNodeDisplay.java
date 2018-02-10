package com.github.jaystgelais.jrpg.conversation.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.conversation.ConversationNode;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.ui.PopupPanel;
import com.github.jaystgelais.jrpg.ui.text.TextDisplay;
import com.github.jaystgelais.jrpg.ui.text.transition.TypedTextTransition;

import java.util.Optional;

final class ConversationNodeDisplay extends PopupPanel {
    private final ConversationNode node;
    private TextDisplay textDisplay;

    public ConversationNodeDisplay(final ConversationNode node, final Skin skin, final float x, final float y,
                                   final float width, final float height) {
        super(skin, x, y, width, height);
        this.node = node;
    }

    public ConversationNodeDisplay(final ConversationNode node, final Skin skin) {
        super(skin);
        this.node = node;
    }

    public ConversationNodeDisplay(final ConversationNode node) {
        super();
        this.node = node;
    }

    @Override
    protected Actor buildLayout(final Skin skin) {
        final BitmapFont font = skin.getFont("conversation");
        textDisplay = new TextDisplay(font, node.getLineTextList(), new TypedTextTransition());
        Table layout = new Table();
        layout.setFillParent(true);
        layout.pad(font.getLineHeight() / 2);
        final Label speakerLabel = new Label(
                node.getSpeaker().getName(),
                skin.get("conversation", Label.LabelStyle.class)
        );
        speakerLabel.setAlignment(Align.left);
        layout.add(speakerLabel).fillX().spaceBottom(font.getLineHeight() / 2);
        layout.row();
        layout.add(textDisplay).fill().expand();

        return layout;
    }

    @Override
    protected Optional<Updatable> getUpdatable() {
        return Optional.ofNullable(textDisplay);
    }

    @Override
    protected Optional<InputHandler> getInputHandler() {
        return Optional.ofNullable(textDisplay);
    }

    @Override
    protected boolean isDisplayComplete() {
        return textDisplay != null && textDisplay.isEmpty();
    }
}
