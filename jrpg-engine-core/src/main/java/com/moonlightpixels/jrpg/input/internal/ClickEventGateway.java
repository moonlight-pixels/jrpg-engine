package com.moonlightpixels.jrpg.input.internal;

import com.moonlightpixels.jrpg.input.ClickEvent;

public interface ClickEventGateway {
    void fireEvent(ClickEvent event);
}
