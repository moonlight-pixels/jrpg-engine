package com.moonlightpixels.jrpg.map.character.internal;

import com.moonlightpixels.jrpg.map.character.CharacterActor;

public interface CharacterController {
    CharacterCommand getNextCommand(CharacterActor characterActor);
}
