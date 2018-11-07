package com.moonlightpixels.jrpg.ui;

import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonlightpixels.jrpg.input.ClickEvent;
import com.moonlightpixels.jrpg.input.ControlEvent;
import com.moonlightpixels.jrpg.input.InputScheme;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A menu is a hierarchical set of UserInterfaces.
 */
public final class Menu implements InputHandler {
    private final UserInterface userInterface;
    private final MenuState initialState;
    private final Consumer<Menu> onClose;
    private final Map<StoreKey, Object> store = new HashMap<>();
    private final Set<Actor> openActors = new HashSet<>();
    private StateMachine<Menu, MenuState> stateMachine = null;
    private InputScheme inputScheme;

    /**
     * Constructs new Menu with the given initial state.
     *
     * @param userInterface Game's UserInterface
     * @param initialState Menu's initial state
     */
    public Menu(final UserInterface userInterface, final MenuState initialState) {
        this(userInterface, initialState, null);
    }

    /**
     * Constructs new Menu with the given initial state and on-close action.
     *
     * @param userInterface Game's UserInterface
     * @param initialState Menu's initial state
     * @param onClose Menu's on-close action
     */
    public Menu(final UserInterface userInterface, final MenuState initialState, final Consumer<Menu> onClose) {
        this.userInterface = userInterface;
        this.initialState = initialState;
        this.onClose = onClose;
    }

    /**
     * Opens menu to initial state.
     */
    public void open() {
        stateMachine = new StackStateMachine<>(this, initialState);
        initialState.enter(this);
        initState();
    }

    /**
     * Open new sub-menu state.
     *
     * @param menuState Menu State to open
     */
    public void open(final MenuState menuState) {
        if (isOpen()) {
            stateMachine.changeState(menuState);
            initState();
        }
    }

    /**
     * Close this menu and all open sub-menu states.
     */
    public void close() {
        openActors.forEach(userInterface::remove);
        openActors.clear();
        stateMachine = null;
        if (onClose != null) {
            onClose.accept(this);
        }
    }

    /**
     * Adds a tracked Actor to the UserInterface. All tracked Actors will be removed open close of menu.
     *
     * @param actor Actor to add.
     */
    public void addActor(final Actor actor) {
        userInterface.add(actor);
        openActors.add(actor);
    }

    /**
     * Removes a tracked Actor from the UserInterface.
     *
     * @param actor Actor to remove
     */
    public void removeActor(final Actor actor) {
        userInterface.remove(actor);
        openActors.remove(actor);
    }

    /**
     * Stores data to the menu store.
     *
     * <p>The menu store stores keys by type. This method uses value.getClass to determine storage type.</p>
     *
     * @param key Name key for stored value
     * @param value value to store
     */
    public void store(final String key, final Object value) {
        store(key, value, value.getClass());
    }

    /**
     * Stores data to the menu store with the provided type.
     *
     * @param key Name key for stored value
     * @param value value to store
     * @param type type of value to store
     * @param <T> type of value to store
     */
    public <T> void store(final String key, final T value, final Class<? extends T> type) {
        store.put(new StoreKey(type, key), value);
    }

    /**
     * Retrieve data from menu store.
     *
     * @param key Name key for stored value
     * @param type type of value stored
     * @param <T> type of value stored
     * @return stored value.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getFromStore(final String key, final Class<T> type) {
        return Optional.ofNullable((T) store.get(new StoreKey(type, key)));
    }

    /**
     * UserInterface tis Menu's actors need to join.
     *
     * @return UserInterface
     */
    public UserInterface getUserInterface() {
        return userInterface;
    }

    @Override
    public boolean handleControlEvent(final ControlEvent event) {
        if (isOpen()) {
            if (event == ControlEvent.CANCEL_PRESSED && stateMachine.getCurrentState().isDismissible()) {
                dismissCurrentState();
                return true;
            } else {
                return stateMachine.getCurrentState().getInputHandler().handleControlEvent(event);
            }
        }
        return false;
    }

    @Override
    public boolean handleClickEvent(final ClickEvent event) {
        if (isOpen()) {
            return stateMachine.getCurrentState().getInputHandler().handleClickEvent(event);
        }
        return false;
    }

    @Override
    public void setInputScheme(final InputScheme inputScheme) {
        this.inputScheme = inputScheme;
        if (isOpen()) {
            stateMachine.getCurrentState().getInputHandler().setInputScheme(inputScheme);
        }
    }

    private void initState() {
        stateMachine.getCurrentState().getInputHandler().setInputScheme(inputScheme);
    }

    private boolean isOpen() {
        return (stateMachine != null);
    }

    private void dismissCurrentState() {
        if (!stateMachine.revertToPreviousState()) {
            close();
        }
    }

    @EqualsAndHashCode
    private static final class StoreKey {
        private final Class<?> type;
        private final String key;

        private StoreKey(final Class<?> type, final String key) {
            this.type = type;
            this.key = key;
        }
    }
}
