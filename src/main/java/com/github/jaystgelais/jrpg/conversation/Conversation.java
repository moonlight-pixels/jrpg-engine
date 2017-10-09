package com.github.jaystgelais.jrpg.conversation;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("Conversation (%d):%n", nodes.size()));
        nodes.forEach(node -> {
            sb.append(String.format("    %s:%n", node.getSpeaker()));
            node.getLineTextList().forEach(line -> {
                sb.append(String.format("    %s%n", line));
            });
            sb.append("\n");
        });

        return sb.toString();
    }
}
