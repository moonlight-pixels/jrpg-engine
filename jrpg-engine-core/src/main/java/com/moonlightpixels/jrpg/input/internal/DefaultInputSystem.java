package com.moonlightpixels.jrpg.input.internal;

import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import com.moonlightpixels.jrpg.input.InputSystem;
import com.moonlightpixels.jrpg.input.KeyboardMapping;
import com.moonlightpixels.jrpg.internal.gdx.GdxFacade;

import java.util.Optional;

public final class DefaultInputSystem implements InputSystem, ControlEventGateway {
    private final GdxFacade gdx;
    private InputScheme inputScheme;
    private ControlEvent lastControlEvent;

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
        gdx.getInput().setInputProcessor(new DefaultInputProcessor(this, keyboardMapping));
    }

    @Override
    public void fireEvent(final ControlEvent controlEvent) {
        if (inputScheme == InputScheme.Keyboard) {
            lastControlEvent = controlEvent;
        }
    }

    @Override
    public Optional<ControlEvent> readControlEvent() {
        Optional<ControlEvent> event = Optional.ofNullable(lastControlEvent);
        lastControlEvent = null;
        return event;
    }
}
