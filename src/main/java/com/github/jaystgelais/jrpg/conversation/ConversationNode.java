package com.github.jaystgelais.jrpg.conversation;

import java.util.LinkedList;
import java.util.List;

public final class ConversationNode {
    private final Speaker speaker;
    private final List<String> lineTextList;

    public ConversationNode(final Speaker speaker, final List<String> lineTextList) {
        this.speaker = speaker;
        this.lineTextList = new LinkedList<>();
        this.lineTextList.addAll(lineTextList);
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public List<String> getLineTextList() {
        return lineTextList;
    }
}
