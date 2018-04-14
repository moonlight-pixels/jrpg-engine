package com.moonlightpixels.jrpg.legacy.map.animation;

public interface DoorLockCheck {
    DoorLockCheck LOCKED = () -> true;
    DoorLockCheck UNLOCKED = () -> false;

    boolean isLocked();
}
