package com.moonlightpixels.jrpg.legacy.tween;

import com.moonlightpixels.jrpg.legacy.state.Updatable;

public interface Tween<T> extends Updatable {
    T getValue();
    boolean isComplete();
}
