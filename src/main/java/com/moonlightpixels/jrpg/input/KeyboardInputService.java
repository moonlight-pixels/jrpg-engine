package com.moonlightpixels.jrpg.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import java.util.HashMap;
import java.util.Map;

public final class KeyboardInputService implements InputService {
    public static final int DEFAULT_INPUT_DELAY_MS = 50;
    public static final int DEFAULT_UP_KEY = Keys.UP;
    public static final int DEFAULT_DOWN_KEY = Keys.DOWN;
    public static final int DEFAULT_LEFT_KEY = Keys.LEFT;
    public static final int DEFAULT_RIGHT_KEY = Keys.RIGHT;
    public static final int DEFAULT_OK_KEY = Keys.ENTER;
    public static final int DEFAULT_CANCEL_KEY = Keys.TAB;
    public static final int DEFAULT_MENU_KEY = Keys.SPACE;
    public static final int DEFAULT_PAUSE_KEY = Keys.ESCAPE;
    public static final int DEFAULT_SCREENSHOT_KEY = Keys.NUM_1;

    private final Map<Inputs, Integer> keyMap;

    public KeyboardInputService() {
        this(getDefaultKeyMap());
    }

    public KeyboardInputService(final Map<Inputs, Integer> keyMap) {
        this.keyMap = keyMap;
    }

    @Override
    public boolean isPressed(final Inputs input) {
        return Gdx.input.isKeyPressed(keyMap.get(input));
    }

    private static Map<Inputs, Integer> getDefaultKeyMap() {
        Map<Inputs, Integer> keyMap = new HashMap<>();
        keyMap.put(Inputs.UP,         DEFAULT_UP_KEY);
        keyMap.put(Inputs.DOWN,       DEFAULT_DOWN_KEY);
        keyMap.put(Inputs.LEFT,       DEFAULT_LEFT_KEY);
        keyMap.put(Inputs.RIGHT,      DEFAULT_RIGHT_KEY);
        keyMap.put(Inputs.OK,         DEFAULT_OK_KEY);
        keyMap.put(Inputs.CANCEL,     DEFAULT_CANCEL_KEY);
        keyMap.put(Inputs.MENU,       DEFAULT_MENU_KEY);
        keyMap.put(Inputs.PAUSE,      DEFAULT_PAUSE_KEY);
        keyMap.put(Inputs.SCREENSHOT, DEFAULT_SCREENSHOT_KEY);

        return keyMap;
    }
}
