package com.moonlightpixels.jrpg.legacy.map.script;

import com.moonlightpixels.jrpg.legacy.conversation.ConversationProvider;
import com.moonlightpixels.jrpg.legacy.map.GameMap;

public final class SimpleConversationSceneFactory implements SceneFactory {
    private final GameMap map;
    private final String npcName;
    private final ConversationProvider conversationProvider;

    public SimpleConversationSceneFactory(final GameMap map, final String npcName,
                                          final ConversationProvider conversationProvider) {
        this.map = map;
        this.npcName = npcName;
        this.conversationProvider = conversationProvider;
    }

    @Override
    public Scene createScene() {
        return new Scene(
                new FaceActorTowardsHeroCommand(map, npcName),
                new ConversationCommand(conversationProvider)
        );
    }
}
