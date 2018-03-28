package com.github.jaystgelais.jrpg.combat.svb;

import com.github.jaystgelais.jrpg.Game;
import com.github.jaystgelais.jrpg.combat.Battle;
import com.github.jaystgelais.jrpg.combat.BattleSystem;
import com.github.jaystgelais.jrpg.combat.Encounter;
import com.github.jaystgelais.jrpg.combat.EnemyLayout;
import com.github.jaystgelais.jrpg.combat.action.ActionTypeProvider;
import com.github.jaystgelais.jrpg.combat.action.CombatActionType;
import com.github.jaystgelais.jrpg.combat.action.TargetChoiceProvider;
import com.github.jaystgelais.jrpg.combat.action.Targetable;
import com.github.jaystgelais.jrpg.combat.action.TargetableChoiceProvider;
import com.github.jaystgelais.jrpg.combat.svb.ui.SVBDialogProviderFactory;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.input.InputService;
import com.github.jaystgelais.jrpg.party.PlayerCharacter;
import com.github.jaystgelais.jrpg.ui.dialog.Dialog;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class SideViewBattleSystem extends BattleSystem {
    private final long messageDisplayMs;
    private final SVBDialogProviderFactory<CombatActionType> actionDialogProviderFactory;
    private final TargetableChoiceProviderDialogMapper targetableChoiceProviderDialogMapper;
    private final SideViewPartyLayout partyLayout;
    private Battle battle;
    private EnemyLayout enemyLayout;
    private Map<PlayerCharacter, SideViewBattleActor> playerActorMap;
    private Dialog activeDialog;

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
        enemyLayout = encounter.getLayout();
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final ActionTypeProvider provider) {
        activeDialog = actionDialogProviderFactory
                .getDialogProvider(playerCharacter, provider::setActionType)
                .getDialog();
        Game.getInstance().getUserInterface().add(activeDialog.getLayout());
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter,
                                  final TargetableChoiceProvider<? extends Targetable> provider) {
        activeDialog = targetableChoiceProviderDialogMapper.getDialog(provider, playerCharacter);
        Game.getInstance().getUserInterface().add(activeDialog.getLayout());
    }

    @Override
    public void handlePlayerInput(final PlayerCharacter playerCharacter, final TargetChoiceProvider provider) {
        
    }

    @Override
    public void handleInput(final InputService inputService) {
        getActiveDialog().ifPresent(dialog -> dialog.handleInput(inputService));
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        enemyLayout.renderEnemies(graphicsService, battle.getEnemies());
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
        getActiveDialog().ifPresent(dialog -> {
            if (dialog.isComplete()) {
                Game.getInstance().getUserInterface().remove(activeDialog.getLayout());
                activeDialog = null;
            }
        });

        battle
                .getParty()
                .getMembers()
                .forEach(playerCharacter -> playerActorMap.get(playerCharacter).update(elapsedTime));
    }

    private Optional<Dialog> getActiveDialog() {
        return Optional.ofNullable(activeDialog);
    }
}
