package com.moonlightpixels.jrpg.input.internal;

import com.moonlightpixels.jrpg.input.ControlEvent;

public interface ControlEventGateway {
    void fireEvent(ControlEvent event);
}
