package com.moonlightpixels.jrpg.input.internal;

public interface EventGateway<T> {
    void fireEvent(T event);
}
