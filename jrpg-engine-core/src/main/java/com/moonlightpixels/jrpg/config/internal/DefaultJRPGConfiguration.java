package com.moonlightpixels.jrpg.config.internal;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.config.JRPGConfiguration;
import com.moonlightpixels.jrpg.config.LaunchConfig;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class DefaultJRPGConfiguration implements JRPGConfiguration {
    private LaunchConfig launchConfig;

    @Override
    public void validate() throws IllegalStateException {
        Preconditions.checkState(launchConfig != null, "Must provide a LaunchConfig.");
    }
}
