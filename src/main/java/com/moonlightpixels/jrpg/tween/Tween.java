package com.moonlightpixels.jrpg.tween;

import com.moonlightpixels.jrpg.state.Updatable;

public interface Tween<T> extends Updatable {
    T getValue();
    boolean isComplete();
}
