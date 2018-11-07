package com.moonlightpixels.jrpg.ui.standard;

import com.moonlightpixels.jrpg.ui.Menu;
import com.moonlightpixels.jrpg.ui.UserInterface;

public interface StandardMenu {
    /**
     * Get this standard Menu.
     *
     * @param userInterface Game's UserInterface
     * @return this standard Menu
     */
    Menu getMenu(UserInterface userInterface);
}
