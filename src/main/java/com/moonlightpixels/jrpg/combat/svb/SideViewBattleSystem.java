package com.moonlightpixels.jrpg.combat.svb;

import com.moonlightpixels.jrpg.Game;
import com.moonlightpixels.jrpg.combat.Battle;
import com.moonlightpixels.jrpg.combat.BattleSystem;
import com.moonlightpixels.jrpg.combat.Combatant;
import com.moonlightpixels.jrpg.combat.Encounter;
import com.moonlightpixels.jrpg.combat.action.ActionTypeProvider;
import com.moonlightpixels.jrpg.combat.action.AllowedTargets;
import com.moonlightpixels.jrpg.combat.action.CombatActionType;
import com.moonlightpixels.jrpg.combat.action.TargetChoiceProvider;
import com.moonlightpixels.jrpg.combat.action.Targetable;
import com.moonlightpixels.jrpg.combat.action.TargetableChoiceProvider;
import com.moonlightpixels.jrpg.combat.enemy.Enemy;
import com.moonlightpixels.jrpg.combat.svb.ui.SVBDialogProviderFactory;
import com.moonlightpixels.jrpg.graphics.GraphicsService;
import com.moonlightpixels.jrpg.input.InputService;
import com.moonlightpixels.jrpg.party.PlayerCharacter;
import com.moonlightpixels.jrpg.ui.UserInputHandler;
import com.moonlightpixels.jrpg.ui.dialog.Dialog;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class SideViewBattleSystem extends BattleSystem {
    private final long messageDisplayMs;
    private final SVBDialogProviderFactory<CombatActionType> actionDialogProviderFactory;
    private final TargetableChoiceProviderDialogMapper targetableChoiceProviderDialogMapper;
    private final SideViewPartyLayout partyLayout;
    private Battle battle;
    private Map<PlayerCharacter, SideViewBattleActor> playerActorMap;
    private Map<Enemy, SideViewBattleEnemy> enemyActorMap;
    private UserInputHandler activeUserInputHandler;

    public SideViewBattleSystem(final long turnLengthMs, final long messageDisplayMs,
                                final SVBDialogProviderFactory<CombatActionType> actionDialogProviderFactory,
                                final TargetableChoiceProviderDialogMapper targetableChoiceProviderDialogMapper,
                                final SideViewPartyLayout partyLayout) {
        super(turnLengthMs);
        this.messageDisplayMs = messageDisplayMs;
        this.actionDialogProviderFactory = actionDialogProviderFactory;
        this.targetableChoiceProviderDialogMapper = targetableChoiceProviderDialogMapper;
        this.partyLayout = partyLayout;
    }

    @Override
    public void configureBattle(final Battle battle) {
        this.battle = battle;
        this.playerActorMap = partyLayout
                .getPlayerPositions(battle.getParty())
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> new SideViewBattleActor(
                                        entry.getKey().getBattlerSpriteSetDefinition().getSpriteSet(
                                                Game.getInstance().getGraphicsService().getAssetManager()
                                        ),
                                        entry.getValue().getX(),
                                        partyLayout.getActionX(),
                                        entry.getValue().getY()
                                )
                        )
                );
    }

    @Override
    public void registerEncounter(final Encounter encounter) {
        this.enemyActorMap = encounter.getLayout()
                .getEnemyPositions(battle.getEnemies())
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> new SideViewBattleEnemy(
                                        entry.getKey().getImage().getTexture(
                                                Game.getInstance().getGraphicsService().getAssetManager()
                                        ),
                                        entry.getValue().getX(),
                                        entry.getValue().getY()
                                )
                        )
                );
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final ActionTypeProvider provider) {
        final Dialog<CombatActionType> dialog = actionDialogProviderFactory
                .getDialogProvider(playerCharacter, provider::setActionType)
                .getDialog();
        activeUserInputHandler = dialog;
        Game.getInstance().getUserInterface().add(dialog.getLayout());
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter,
                                  final TargetableChoiceProvider<? extends Targetable> provider) {
        final Dialog dialog = targetableChoiceProviderDialogMapper.getDialog(provider, playerCharacter);
        activeUserInputHandler = dialog;
        Game.getInstance().getUserInterface().add(dialog.getLayout());
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final TargetChoiceProvider provider,
                                  final AllowedTargets allowedTargets) {
        battle.getFixedTargetsForAllowedTargets(playerCharacter, allowedTargets).ifPresent(provider::setTargets);
        if (!provider.isComplete()) {
            activeUserInputHandler = new TargetSelector(
                    provider,
                    this,
                    battle.getEligibleTargets(playerCharacter, allowedTargets)
            );
        }
    }

    @Override
    public void handleInput(final InputService inputService) {
        getActiveUserInputHandler().ifPresent(dialog -> dialog.handleInput(inputService));
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        battle.getEnemies().forEach(enemy -> enemyActorMap.get(enemy).render(graphicsService));
        battle
                .getParty()
                .getMembers()
                .forEach(playerCharacter -> playerActorMap.get(playerCharacter).render(graphicsService));
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(final long elapsedTime) {
        getActiveUserInputHandler().ifPresent(dialog -> {
            if (dialog.isComplete()) {
                activeUserInputHandler = null;
            }
        });

        battle
                .getParty()
                .getMembers()
                .forEach(playerCharacter -> playerActorMap.get(playerCharacter).update(elapsedTime));
    }

    private Optional<UserInputHandler> getActiveUserInputHandler() {
        return Optional.ofNullable(activeUserInputHandler);
    }

    Optional<SelectableActor> getSelectableActor(final Combatant combatant) {
        if (combatant instanceof PlayerCharacter) {
            return Optional.ofNullable(playerActorMap.get(combatant));
        }

        if (combatant instanceof Enemy) {
            return Optional.ofNullable(enemyActorMap.get(combatant));
        }

        return Optional.empty();
    }
}
