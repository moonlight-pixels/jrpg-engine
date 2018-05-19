package com.moonlightpixels.jrpg.input;

import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Stores the mapping of Keyboard Key Codes to logical control buttons.
 */
public final class KeyboardMapping {
    /**
     * A reasonable default mapping.
     */
    public static final KeyboardMapping DEFAULT = new KeyboardMapping()
        .registerUpKey(Input.Keys.UP)
        .registerDownKey(Input.Keys.DOWN)
        .registerLeftKey(Input.Keys.LEFT)
        .registerRightKey(Input.Keys.RIGHT)
        .registerActionKey(Input.Keys.ENTER)
        .registerCancelKey(Input.Keys.TAB)
        .registerMenuKey(Input.Keys.SPACE);

    private final Map<Key, KeyBinding> bindings = new HashMap<>();

    /**
     * Registers the key corresponding to the logical UP control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerUpKey(final int keyCode) {
        bindings.put(
            Key.UP,
            new KeyBinding(
                keyCode,
                ControlEvent.UP_PRESSED,
                ControlEvent.UP_RELEASED
            )
        );

        return this;
    }

    /**
     * Registers the key corresponding to the logical DOWN control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerDownKey(final int keyCode) {
        bindings.put(
            Key.DOWN,
            new KeyBinding(
                keyCode,
                ControlEvent.DOWN_PRESSED,
                ControlEvent.DOWN_RELEASED
            )
        );

        return this;
    }

    /**
     * Registers the key corresponding to the logical LEFT control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerLeftKey(final int keyCode) {
        bindings.put(
            Key.LEFT,
            new KeyBinding(
                keyCode,
                ControlEvent.LEFT_PRESSED,
                ControlEvent.LEFT_RELEASED
            )
        );

        return this;
    }

    /**
     * Registers the key corresponding to the logical RIGHT control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerRightKey(final int keyCode) {
        bindings.put(
            Key.RIGHT,
            new KeyBinding(
                keyCode,
                ControlEvent.RIGHT_PRESSED,
                ControlEvent.RIGHT_RELEASED
            )
        );

        return this;
    }

    /**
     * Registers the key corresponding to the logical ACTION control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerActionKey(final int keyCode) {
        bindings.put(Key.ACTION, new KeyBinding(keyCode, ControlEvent.ACTION_PRESSED));
        return this;
    }

    /**
     * Registers the key corresponding to the logical CANCEL control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerCancelKey(final int keyCode) {
        bindings.put(Key.CANCEL, new KeyBinding(keyCode, ControlEvent.CANCEL_PRESSED));
        return this;
    }

    /**
     * Registers the key corresponding to the logical MENU control button.
     *
     * @param keyCode Code for mapped key.
     * @return This KeyboardMapping for chaining calls.
     */
    public KeyboardMapping registerMenuKey(final int keyCode) {
        bindings.put(Key.MENU, new KeyBinding(keyCode, ControlEvent.MENU_PRESSED));
        return this;
    }

    /**
     * Returns the associated Pressed event if one is mapped ot this key.
     *
     * @param keyCode Key code for key that was pressed
     * @return Optional wrapping the associated Pressed event if one is mapped ot this key
     */
    public Optional<ControlEvent> getPressedEvent(final int keyCode) {
        return bindings
            .values()
            .stream()
            .filter(keyBinding -> keyBinding.getKeyCode() == keyCode)
            .findFirst()
            .flatMap(KeyBinding::getPressedControlEvent);
    }

    /**
     * Returns the associated Released event if one is mapped ot this key.
     *
     * @param keyCode Key code for key that was released
     * @return Optional wrapping the associated Released event if one is mapped ot this key
     */
    public Optional<ControlEvent> getReleasedEvent(final int keyCode) {
        return bindings
            .values()
            .stream()
            .filter(keyBinding -> keyBinding.getKeyCode() == keyCode)
            .findFirst()
            .flatMap(KeyBinding::getReleasedControlEvent);
    }

    private enum Key {
        UP, DOWN, LEFT, RIGHT, ACTION, CANCEL, MENU
    }

    private static final class KeyBinding {
        private final int keyCode;
        private final ControlEvent pressedControlEvent;
        private final ControlEvent releasedControlEvent;

        private KeyBinding(final int keyCode, final ControlEvent pressedControlEvent,
                           final ControlEvent releasedControlEvent) {
            this.keyCode = keyCode;
            this.pressedControlEvent = pressedControlEvent;
            this.releasedControlEvent = releasedControlEvent;
        }

        private KeyBinding(final int keyCode, final ControlEvent pressedControlEvent) {
            this(keyCode, pressedControlEvent, null);
        }

        public int getKeyCode() {
            return keyCode;
        }

        public Optional<ControlEvent> getPressedControlEvent() {
            return Optional.ofNullable(pressedControlEvent);
        }

        public Optional<ControlEvent> getReleasedControlEvent() {
            return Optional.ofNullable(releasedControlEvent);
        }
    }
}
