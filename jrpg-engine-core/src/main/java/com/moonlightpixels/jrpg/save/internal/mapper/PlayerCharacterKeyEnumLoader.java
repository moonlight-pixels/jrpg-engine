package com.moonlightpixels.jrpg.save.internal.mapper;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;
import com.moonlightpixels.jrpg.player.PlayerCharacter;

public final class PlayerCharacterKeyEnumLoader implements PlayerCharacterKeyLoader {
    private final Class<? extends Enum> enumType;

    /**
     * Creates a PlayerCharacterKeyEnumLoader for the specific enum type.
     *
     * @param enumType Type of enum to load value as.
     * @throws NullPointerException if {@code enumType} is null
     * @throws IllegalArgumentException if the specified enum type does not
     *                                  implement {@link com.moonlightpixels.jrpg.player.PlayerCharacter.Key}
     */
    public PlayerCharacterKeyEnumLoader(final Class<? extends Enum> enumType) {
        Preconditions.checkNotNull(enumType, "enumType must not be null");
        Preconditions.checkArgument(PlayerCharacter.Key.class.isAssignableFrom(enumType));
        this.enumType = enumType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PlayerCharacter.Key load(final String savedValue) throws SavedStateLoadExcpetion {
        try {
            return (PlayerCharacter.Key) Enum.valueOf(enumType, savedValue);
        } catch (Throwable t) {
            throw new SavedStateLoadExcpetion(
                String.format("Unable to load PlayerCharacter Key with value %s", savedValue),
                t
            );
        }
    }
}
