package com.moonlightpixels.jrpg.frontend.internal;

import com.moonlightpixels.jrpg.frontend.FrontEndConfig;
import lombok.Data;

import java.util.Optional;

@Data
public final class DefaultFrontEndConfig implements FrontEndConfig {
    private String titleScreenPath;

    @Override
    public Optional<String> getTitleScreenPath() {
        return Optional.ofNullable(titleScreenPath);
    }
}
