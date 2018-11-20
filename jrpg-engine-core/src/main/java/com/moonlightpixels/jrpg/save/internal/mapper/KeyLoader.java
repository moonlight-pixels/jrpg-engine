package com.moonlightpixels.jrpg.save.internal.mapper;

import com.moonlightpixels.jrpg.save.internal.SavedStateLoadExcpetion;

public interface KeyLoader<K> {
    K load(String savedValue) throws SavedStateLoadExcpetion;
}
