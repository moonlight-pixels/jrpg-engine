package com.github.jaystgelais.jrpg.map.script;

import com.github.jaystgelais.jrpg.conversation.ConversationProvider;
import com.github.jaystgelais.jrpg.map.GameMap;

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
