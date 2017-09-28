package com.github.jaystgelais.jrpg.conversation;

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
            nodes.add(currentNode);
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
        if (currentNode == null) {
            throw new IllegalStateException("Cannot build empty conversation");
        }

        nodes.add(currentNode);

        return new Conversation(nodes);
    }

    private ConversationNode startNewNode(final String name) {
        return new ConversationNode(new Speaker(name), Collections.emptyList());
    }
}
