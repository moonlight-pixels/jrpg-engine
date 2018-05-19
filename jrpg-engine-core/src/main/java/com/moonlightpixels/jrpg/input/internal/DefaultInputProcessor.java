package com.moonlightpixels.jrpg.input.internal;

import com.badlogic.gdx.InputAdapter;
import com.moonlightpixels.jrpg.input.KeyboardMapping;
import lombok.EqualsAndHashCode;

import javax.inject.Inject;

@EqualsAndHashCode
public final class DefaultInputProcessor extends InputAdapter {
    private final ControlEventGateway controlEventGateway;
    private final KeyboardMapping keyboardMapping;

    @Inject
    public DefaultInputProcessor(final ControlEventGateway controlEventGateway,
                                 final KeyboardMapping keyboardMapping) {
        this.controlEventGateway = controlEventGateway;
        this.keyboardMapping = keyboardMapping;
    }

    @Override
    public boolean keyDown(final int keycode) {
        keyboardMapping.getPressedEvent(keycode).ifPresent(controlEventGateway::fireEvent);
        return true;
    }

    @Override
    public boolean keyUp(final int keycode) {
        keyboardMapping.getReleasedEvent(keycode).ifPresent(controlEventGateway::fireEvent);
        return true;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return true;
    }
}
