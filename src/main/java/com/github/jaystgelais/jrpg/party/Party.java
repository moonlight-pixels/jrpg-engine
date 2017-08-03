package com.github.jaystgelais.jrpg.party;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Party {
    private final int maxSize;
    private final List<Character> members;

    public Party(int maxSize) {
        this.maxSize = maxSize;
        members = new LinkedList<>();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public List<Character> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void addMember(final Character member) {
        if (members.size() >= maxSize) {
            throw new IllegalStateException("Party is already full");
        }
        if (members.contains(member)) {
            throw new IllegalStateException("Member already in party");
        }
        members.add(member);
    }

    public void removeMember(final Character member) {
        if (!members.contains(member)) {
            throw new IllegalStateException("Member not in party");
        }
        members.remove(member);
    }

    public Character getLeader() {
        if (members.isEmpty()) {
            throw new IllegalStateException("Party is empty");
        }

        return members.get(0);
    }
}
