package com.github.jaystgelais.jrpg.conversation.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.conversation.Conversation;
import com.github.jaystgelais.jrpg.conversation.ConversationNode;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputHandler;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.state.Updatable;
import com.github.jaystgelais.jrpg.ui.Panel;
import com.github.jaystgelais.jrpg.ui.text.TextDisplay;
import com.github.jaystgelais.jrpg.ui.text.transition.TypedTextTransition;

public final class ConversationDisplay implements Updatable, InputHandler {
    private static final int DEFAULT_PANEL_TOP_MARGIN = 10;
    private static final int DEFAULT_TRANSITION_TIME_MS = 400;
    private static final float DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN = 2;
    private static final float DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN = 4;

    private final Conversation conversation;
    private final Skin skin;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private TextDisplay textDisplay;

    public ConversationDisplay(final Conversation conversation, final Skin skin,
                               final float x, final float y,
                               final float width, final float height) {
        this.conversation = conversation;
        this.skin = skin;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        layoutNextNode();
    }

    public ConversationDisplay(final Conversation conversation, final Skin skin) {
        this(conversation, skin, getDefaultX(), getDefaultY(), getDefaultWidth(), getDefaultHeight());
    }

    public ConversationDisplay(final Conversation conversation) {
        this(conversation, getDefaultSkin());
    }

    private void layoutNextNode() {
        ConversationNode node = conversation.getNextNode();
        if (node == null) {
            textDisplay = null;
        } else {
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
            Game.getInstance().getGraphicsService().registerUI(createPanel(layout).bottom().left());
        }
    }

    private <T extends Actor>  Panel<T> createPanel(final T content) {
        final Panel<T> panel = new Panel<>(content, skin.get("conversation", Panel.PanelStyle.class));
        panel.setBounds(x, y, width, height);
        return panel;
    }

    public boolean isComplete() {
        return textDisplay == null;
    }

    @Override
    public void update(final long elapsedTime) {
        if (textDisplay != null) {
            textDisplay.update(elapsedTime);

            if (textDisplay.isEmpty()) {
                layoutNextNode();
            }
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        if (textDisplay != null) {
            textDisplay.handleInput(inputService);
        }
    }

    private static float getDefaultWidth() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return graphicsService.getResolutionWidth() / DEFAULT_PANEL_WIDTH_AS_PORTION_OF_SCREEN;
    }

    private static float getDefaultHeight() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return graphicsService.getResolutionHeight() / DEFAULT_PANEL_HEIGHT_AS_PORTION_OF_SCREEN;
    }

    private static float getDefaultX() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return Math.round((graphicsService.getResolutionWidth() - getDefaultWidth()) / 2f);
    }

    private static float getDefaultY() {
        GraphicsService graphicsService = Game.getInstance().getGraphicsService();
        return graphicsService.getResolutionHeight() - getDefaultHeight() - DEFAULT_PANEL_TOP_MARGIN;
    }

    private static Skin getDefaultSkin() {
        final BitmapFont font = Game.getInstance().getGraphicsService().getFontSet().getTextFont();

        Skin skin = new Skin();
        skin.add("conversation", new Label.LabelStyle(font, font.getColor()));
        skin.add("conversation", font);
        skin.add("conversation", new Panel.PanelStyle(Panel.getDefaultBackground()));

        return skin;
    }

    @Override
    public String toString() {
        return String.format("ConversationDisplay{conversation=%s}", conversation);
    }
}
