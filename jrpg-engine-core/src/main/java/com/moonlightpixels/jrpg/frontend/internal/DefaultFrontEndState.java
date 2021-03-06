package com.moonlightpixels.jrpg.frontend.internal;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.internal.JRPG;
import com.moonlightpixels.jrpg.internal.gdx.AssetUtil;
import com.moonlightpixels.jrpg.internal.graphics.GraphicsContext;
import com.moonlightpixels.jrpg.ui.Menu;
import com.moonlightpixels.jrpg.ui.UserInterface;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

import static com.moonlightpixels.jrpg.internal.inject.GraphicsModule.FRONTEND;

public final class DefaultFrontEndState implements FrontEndState {
    private final FrontEndConfig frontEndConfig;
    private final UserInterface userInterface;
    private final GraphicsContext graphicsContext;
    private final AssetManager assetManager;
    private final Menu menu;
    private Texture background;

    @Inject
    public DefaultFrontEndState(final FrontEndConfig frontEndConfig,
                                final UserInterface userInterface,
                                @Named(FRONTEND) final GraphicsContext graphicsContext,
                                final AssetManager assetManager,
                                @Named(FRONTEND) final Menu menu) {
        this.frontEndConfig = frontEndConfig;
        this.userInterface = userInterface;
        this.graphicsContext = graphicsContext;
        this.assetManager = assetManager;
        this.menu = menu;
    }

    @Override
    public void enter(final JRPG entity) {
        frontEndConfig.getTitleScreenPath().ifPresent(path -> {
            background = AssetUtil.onDemand(assetManager, path, Texture.class);
        });
        menu.open();
    }

    @Override
    public void update(final JRPG entity) {
        graphicsContext.activate();
        getBackground().ifPresent(background -> {
            graphicsContext.getSpriteBatch().begin();
            graphicsContext.getSpriteBatch().draw(background, 0, 0);
            graphicsContext.getSpriteBatch().end();
        });
        userInterface.update();
    }

    @Override
    public void exit(final JRPG entity) {
        menu.close();
        getBackground().ifPresent(Texture::dispose);
        background = null;
    }

    @Override
    public boolean onMessage(final JRPG entity, final Telegram telegram) {
        return false;
    }

    private Optional<Texture> getBackground() {
        return Optional.ofNullable(background);
    }

    @Override
    public boolean handleControlEvent(final ControlEvent event) {
        return menu.handleControlEvent(event);
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        return menu.handleClickEvent(event);
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) {
        menu.setInputScheme(inputScheme);
    }
}
