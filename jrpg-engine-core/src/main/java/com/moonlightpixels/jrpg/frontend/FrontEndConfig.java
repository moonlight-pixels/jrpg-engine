package com.moonlightpixels.jrpg.frontend;

import java.util.Optional;

/**
 * Configuration of Front End game mode.
 */
public interface FrontEndConfig {
    /**
     * Optionally returns the path the the games titlescreen background. If present, the image at this path will be
     * rendered as part of the title screen.
     *
     * @return Optional path
     */
    Optional<String> getTitleScreenPath();

    /**
     * Sets the path the the games titlescreen background. If present, the image at this path will be
     * rendered as part of the title screen.
     *
     * @param path path the the games titlescreen background
     */
    void setTitleScreenPath(String path);
}
