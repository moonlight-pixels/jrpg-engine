package com.moonlightpixels.jrpg.legacy.party;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Party {
    private final int maxSize;
    private final List<PlayerCharacter> members;

    public Party(final int maxSize) {
        this.maxSize = maxSize;
        members = new LinkedList<>();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getSize() {
        return members.size();
    }

    public List<PlayerCharacter> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void addMember(final PlayerCharacter member) {
        if (members.size() >= maxSize) {
            throw new IllegalStateException("Party is already full");
        }
        if (members.contains(member)) {
            throw new IllegalStateException("Member already in party");
        }
        members.add(member);
    }

    public void removeMember(final PlayerCharacter member) {
        if (!members.contains(member)) {
            throw new IllegalStateException("Member not in party");
        }
        members.remove(member);
    }

    public PlayerCharacter getLeader() {
        if (members.isEmpty()) {
            throw new IllegalStateException("Party is empty");
        }

        return members.get(0);
    }
}
