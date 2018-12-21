package com.moonlightpixels.jrpg.player;

import com.google.common.base.Preconditions;
import com.moonlightpixels.jrpg.player.internal.DefaultPlayerCharacter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Game state object that holds infomraiton on all PlayerCharacters.
 */
@EqualsAndHashCode
@ToString
@SuppressFBWarnings
public final class Cast {
    private Party activeParty;
    private final List<Party> currentParties = new LinkedList<>();
    private final List<PlayerCharacter> roster = new LinkedList<>();
    private final Map<DefaultPlayerCharacter.Key, PlayerCharacter> cast = new HashMap<>();

    /**
     * Returns the game's active Party.
     *
     * @return game's active Party.
     */
    public Party getActiveParty() {
        return activeParty;
    }

    /**
     * Validates that the Cast has been setup with an active party.
     *
     * @return true is active party has been set
     */
    public boolean isValid() {
        return activeParty != null;
    }

    /**
     * Returns the list of current parties.
     *
     * @return curret parties as an unmodifiable list.
     */
    public List<Party> getCurrentParties() {
        return Collections.unmodifiableList(currentParties);
    }

    /**
     * Sets the list of current parties. This method takes a list of other parties in support of multiple party
     * scenarios.
     *
     * @param activeParty The current active party
     * @param others 0 or more inactive parties
     */
    public void configureParties(final Party activeParty, final Party... others) {
        Preconditions.checkNotNull(activeParty, "activeParty must not be null");
        Preconditions.checkArgument(activeParty.isValid(), "activeParty must be valid");
        for (Party party : others) {
            Preconditions.checkNotNull(party, "others must not contain null references");
            Preconditions.checkArgument(party.isValid(), "others must not contain invalid parties");
        }

        currentParties.clear();
        this.activeParty = activeParty;
        currentParties.add(activeParty);
        currentParties.addAll(Arrays.asList(others));

        getRosterAssignedToParties().stream()
            .filter(playerCharacter -> !roster.contains(playerCharacter))
            .forEach(roster::add);
    }

    /**
     * Makes the next party active. Looping back to the begining of the list if necessary.
     */
    public void nextParty() {
        activeParty = currentParties.get(
            (currentParties.indexOf(activeParty) + 1) % currentParties.size()
        );
    }

    /**
     * Makes the previous party active. Looping back to the begining of the end if necessary.
     */
    public void previousParty() {
        activeParty = currentParties.get(
            (currentParties.indexOf(activeParty) + currentParties.size() - 1) % currentParties.size()
        );
    }

    /**
     * Looks up a PlayerCharacter from the case by Key.
     *
     * @param key PlayerCharacter key
     * @return PlayerCharacter (wrapped in Optional)
     */
    public Optional<PlayerCharacter> getPlayerCharacter(final DefaultPlayerCharacter.Key key) {
        return Optional.ofNullable(cast.get(key));
    }

    /**
     * Get roster of characters available for inclusion in parties.
     *
     * @return roster as unmodifiable list
     */
    public List<PlayerCharacter> getRoster() {
        return Collections.unmodifiableList(roster);
    }

    /**
     * Get roster of characters available for inclusion in parties, not yet assigned to a party.
     *
     * @return available roster as unmodifiable list
     */
    public List<PlayerCharacter> getUnassignedRoster() {
        return roster
            .stream()
            .filter(playerCharacter -> !getRosterAssignedToParties().contains(playerCharacter))
            .collect(Collectors.toList());
    }

    /**
     * Add a new PlayerCharacter to the Cast. PlayerCharacters are stored by their key. Add a new PlayerCharacter whose
     * key is the same as a PlayerCharacter already in the cast will replace that PlayerCharacter.
     *
     * @param playerCharacter PlayerCharacter to add.
     */
    public void addToCast(final PlayerCharacter playerCharacter) {
        cast.put(playerCharacter.getKey(), playerCharacter);
    }

    /**
     * Promote a PlayerCharacter form the cast to the roster, making them available to include in parties.
     *
     * @param key Key of PlayerCharacter to add ot roster
     */
    public void addToRoster(final DefaultPlayerCharacter.Key key) {
        final PlayerCharacter playerCharacter = cast.get(key);
        if (!roster.contains(playerCharacter)) {
            roster.add(playerCharacter);
        }
    }

    /**
     * Add a PlayerCharacter to the roster, making them available to include in parties. if the PlayerCharacter is not
     * already a part of the Cast, they will be added.
     *
     * @param playerCharacter PlayerCharacter to add ot roster
     */
    public void addToRoster(final PlayerCharacter playerCharacter) {
        addToCast(playerCharacter);
        addToRoster(playerCharacter.getKey());
    }

    /**
     * Removes a PlayerCharacter from the roster, leaving them in the cast.
     *
     * @param key Key of the PlayerCharacter to remove.
     */
    public void removeFromRoster(final DefaultPlayerCharacter.Key key) {
        getPlayerCharacter(key).ifPresent(roster::remove);
    }

    private List<PlayerCharacter> getRosterAssignedToParties() {
        return currentParties.stream()
            .map(Party::getMembers)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
