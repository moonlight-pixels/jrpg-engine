package com.moonlightpixels.jrpg.player;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.map.Location;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A collection of PlayerCharacters that travel and engage in combat together.
 */
@EqualsAndHashCode
@ToString
@SuppressFBWarnings(justification = "Generated code")
public final class Party {
    @Getter
    private final int minimumSize;
    @Getter
    private final int maximumSize;
    private final List<PlayerCharacter> members;

    @Getter
    private Location location;

    /**
     * Creates a new Party with the given minimum and maximum size as well as their location.
     *
     * @param minimumSize minimum size allowed for this party. Must be greater than 0.
     * @param maximumSize maximum size allowed for this party. Must be greater than or equal to minum size.
     * @param location party's initial location
     */
    public Party(final int minimumSize, final int maximumSize, final Location location) {
        Preconditions.checkArgument(minimumSize > 0, "minimumSize must be greater than 0");
        Preconditions.checkArgument(
            maximumSize >= minimumSize,
            "maximumSize must be greater than or equal to minimumSize"
        );
        Preconditions.checkNotNull(location, "location must not be null");

        this.minimumSize = minimumSize;
        this.maximumSize = maximumSize;
        this.location = location;
        this.members = new ArrayList<>(maximumSize);
    }

    /**
     * Validates that this Party is withing it's size constraints.
     *
     * @return true if Party is valid
     */
    public boolean isValid() {
        return members.size() >= minimumSize;
    }

    /**
     * Sets the Party's current location.
     *
     * @param location new Location
     * @throws NullPointerException if Location is null
     */
    public void setLocation(final Location location) {
        Preconditions.checkNotNull(location, "location must not be null");
        this.location = location;
    }

    /**
     * Get members of this Party.
     *
     * @return members of this Party as unmodifiable List
     */
    public List<PlayerCharacter> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Add PlayerCharacter to this Party if not already in Party.
     *
     * @param member PlayerCharacter to add
     * @throws NullPointerException if member is null
     * @throws IllegalStateException is Party is already full
     * @return true if member was not previously in Party
     */
    public boolean addMember(final PlayerCharacter member) {
        Preconditions.checkNotNull(member, "member must not be null");
        if (members.contains(member)) {
            return false;
        }
        Preconditions.checkState(members.size() < maximumSize, "Party is already full");

        members.add(member);
        return true;
    }

    /**
     * Removes a PlayerCharacter from this Party.
     *
     * @param member PlayerCharacter to move.
     * @throws NullPointerException if member is null
     * @return true if PlayerCharacter had been present in Party.
     */
    public boolean removeMember(final PlayerCharacter member) {
        Preconditions.checkNotNull(member, "member must not be null");

        return members.remove(member);
    }

    /**
     * Swaps the position of 2 PlayerCharacters in the party lineup.
     *
     * @param a PlayerCharacter to swap with b
     * @param b PlayerCharacter to swap with a
     */
    public void swap(final PlayerCharacter a, final PlayerCharacter b) {
        Preconditions.checkState(members.contains(a), "a is not in this Party");
        Preconditions.checkState(members.contains(b), "b is not in this Party");
        final int indexA = members.indexOf(a);
        final int indexB = members.indexOf(b);
        members.set(indexA, b);
        members.set(indexB, a);
    }
}
