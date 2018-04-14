package com.moonlightpixels.jrpg.legacy.conversation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class Conversation {
    private final Queue<ConversationNode> nodes;

    public Conversation(final List<ConversationNode> nodes) {
        this.nodes = new LinkedList<>();
        this.nodes.addAll(nodes);
    }

    public ConversationNode getNextNode() {
        return nodes.poll();
    }
}
