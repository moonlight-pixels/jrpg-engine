package com.moonlightpixels.jrpg.legacy.conversation.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.moonlightpixels.jrpg.legacy.conversation.ConversationNode;
import com.moonlightpixels.jrpg.legacy.input.InputHandler;
import com.moonlightpixels.jrpg.legacy.ui.PopupPanel;
import com.moonlightpixels.jrpg.legacy.ui.UiStyle;
import com.moonlightpixels.jrpg.legacy.state.Updatable;
import com.moonlightpixels.jrpg.legacy.ui.text.TextDisplay;
import com.moonlightpixels.jrpg.legacy.ui.text.transition.TypedTextTransition;

import java.util.Optional;

final class ConversationNodeDisplay extends PopupPanel {
    private final ConversationNode node;
    private TextDisplay textDisplay;

    ConversationNodeDisplay(final ConversationNode node, final float x, final float y,
                                   final float width, final float height) {
        super(x, y, width, height);
        this.node = node;
    }

    ConversationNodeDisplay(final ConversationNode node) {
        super();
        this.node = node;
    }

    @Override
    protected Actor buildLayout() {
        final BitmapFont font = UiStyle.get("conversation", BitmapFont.class);
        textDisplay = new TextDisplay(font, node.getLineTextList(), new TypedTextTransition());
        Table layout = new Table();
        layout.setFillParent(true);
        layout.pad(font.getLineHeight() / 2);
        final Label speakerLabel = new Label(
                node.getSpeaker().getName(),
                UiStyle.get("conversation", Label.LabelStyle.class)
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
