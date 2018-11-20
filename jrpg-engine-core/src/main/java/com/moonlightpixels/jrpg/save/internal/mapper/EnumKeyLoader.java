package com.moonlightpixels.jrpg.save.internal.mapper;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;

public final class EnumKeyLoader<K> implements KeyLoader<K> {
    private final Class<? extends Enum> enumType;

    /**
     * Creates a PlayerCharacterKeyEnumLoader for the specific enum type.
     *
     * @param enumType Type of enum to load value as.
     * @param keyType Type of Key to load
     * @throws NullPointerException if {@code enumType} is null
     * @throws IllegalArgumentException if the specified enum type does not
     *                                  implement {@link com.moonlightpixels.jrpg.player.PlayerCharacter.Key}
     */
    public EnumKeyLoader(final Class<? extends Enum> enumType,
                         final Class<? extends K> keyType) {
        Preconditions.checkNotNull(enumType, "enumType must not be null");
        Preconditions.checkArgument(keyType.isAssignableFrom(enumType));
        this.enumType = enumType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public K load(final String savedValue) throws SavedStateLoadExcpetion {
        try {
            return (K) Enum.valueOf(enumType, savedValue);
        } catch (Throwable t) {
            throw new SavedStateLoadExcpetion(
                String.format("Unable to load PlayerCharacter Key with value %s", savedValue),
                t
            );
        }
    }
}
