package com.moonlightpixels.jrpg.conversation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class ConversationBuilder {
    private final List<ConversationNode> nodes = new LinkedList<>();
    private ConversationNode currentNode;

    public ConversationBuilder speaker(final String name) {
        if (currentNode == null) {
            currentNode = startNewNode(name);
        }

        if (!currentNode.getSpeaker().getName().equals(name)) {
            saveCurrentNode();
            currentNode = startNewNode(name);
        }

        return this;
    }

    public ConversationBuilder say(final String text) {
        if (currentNode == null) {
            throw new IllegalStateException("You must specificy a speaker before saying lines.");
        }

        currentNode.getLineTextList().add(text);

        return this;
    }

    public Conversation build() {
        saveCurrentNode();

        if (nodes.isEmpty()) {
            throw new IllegalStateException("Cannot build empty conversation");
        }

        return new Conversation(nodes);
    }

    private void saveCurrentNode() {
        if (currentNode != null) {
            nodes.add(currentNode);
            currentNode = null;
        }
    }

    private ConversationNode startNewNode(final String name) {
        return new ConversationNode(new Speaker(name), Collections.emptyList());
    }
}
