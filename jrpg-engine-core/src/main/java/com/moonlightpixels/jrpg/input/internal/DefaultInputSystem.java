package com.moonlightpixels.jrpg.input.internal;

import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.input.InputSystem;
import com.moonlightpixels.jrpg.input.KeyboardMapping;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;

import java.util.Optional;

public final class DefaultInputSystem implements InputSystem, ControlEventGateway, ClickEventGateway {
    private final GdxFacade gdx;
    private InputScheme inputScheme;
    private ControlEvent lastControlEvent;
    private ClickEvent lastClickEvent;

    public DefaultInputSystem(final GdxFacade gdx) {
        this.gdx = gdx;
    }

    @Override
    public InputScheme getInputScheme() {
        return inputScheme;
    }

    @Override
    public void useKeyboard(final KeyboardMapping keyboardMapping) {
        inputScheme = InputScheme.Keyboard;
        gdx.getInput().setInputProcessor(new DefaultInputProcessor(this, this, keyboardMapping));
    }

    public void useTouchMouse() {
        inputScheme = InputScheme.TouchMouse;
        gdx.getInput().setInputProcessor(new DefaultInputProcessor(this, this, KeyboardMapping.DEFAULT));
    }

    @Override
    public void fireEvent(final ControlEvent event) {
        if (inputScheme == InputScheme.Keyboard) {
            lastControlEvent = event;
        }
    }

    @Override
    public Optional<ControlEvent> readControlEvent() {
        Optional<ControlEvent> event = Optional.ofNullable(lastControlEvent);
        lastControlEvent = null;
        return event;
    }

    @Override
    public void fireEvent(final ClickEvent event) {
        if (inputScheme == InputScheme.TouchMouse) {
            lastClickEvent = event;
        }
    }

    @Override
    public Optional<ClickEvent> readClickEvent() {
        Optional<ClickEvent> event = Optional.ofNullable(lastClickEvent);
        lastClickEvent = null;
        return event;
    }
}
