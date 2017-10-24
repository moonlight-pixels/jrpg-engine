package com.github.jaystgelais.jrpg.map.animation;

public interface DoorLockCheck {
    DoorLockCheck LOCKED = () -> true;
    DoorLockCheck UNLOCKED = () -> false;

    boolean isLocked();
}
