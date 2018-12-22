package com.moonlightpixels.jrpg.player.internal

import com.moonlightpixels.jrpg.combat.stats.BaseStat
import com.moonlightpixels.jrpg.combat.stats.RequiredStats
import com.moonlightpixels.jrpg.combat.stats.Stat
import com.moonlightpixels.jrpg.combat.stats.StatHolder
import com.moonlightpixels.jrpg.combat.stats.StatSystem
import com.moonlightpixels.jrpg.player.PlayerCharacter
import com.moonlightpixels.jrpg.player.equipment.Equipment
import com.moonlightpixels.jrpg.player.equipment.EquipmentSlot
import com.moonlightpixels.jrpg.player.equipment.EquipmentType
import spock.lang.Specification

class PlayerStatsSpec extends Specification {
    StatSystem statSystem

    void setup() {
        statSystem = new StatSystem()
        statSystem.addStat(
            BaseStat.builder()
                .key(RequiredStats.MaxHP)
                .name('Max HP')
                .shortName('MHP')
                .build()
        )
        statSystem.addStat(
            BaseStat.builder()
                .key(TestStats.MAXMP)
                .name('Max HP')
                .shortName('MHP')
                .build()
        )
    }

    void 'getHolderType() returns the correct type'() {
        expect:
        new PlayerStats(statSystem, [], [(RequiredStats.MaxHP): 100]).getHolderType() == StatHolder.Type.Player
    }

    void 'can update a stat value'() {
        setup:
        PlayerStats playerStats = new PlayerStats(
            statSystem,
            [],
            [
                (RequiredStats.MaxHP): 200
            ]
        )

        when:
        playerStats.setBaseValue(RequiredStats.MaxHP, 250)

        then:
        playerStats.getStatValue(RequiredStats.MaxHP) == 250
    }

    void 'can add/retrieve meters for stats that are registered in StatSystem'() {
        setup:
        PlayerStats playerStats = new PlayerStats(
            statSystem,
            [],
            [
                (RequiredStats.MaxHP): 200,
                (TestStats.MAXMP): 100
            ]
        )

        when:
        playerStats.addMeter(TestStats.MAXMP)

        then:
        playerStats.getMeter(TestStats.MAXMP).value == 100
    }

    void 'correctly applies stat modifiers from equipment'() {
        setup:
        EquipmentSlot equipmentSlot = new EquipmentSlot() {
            @Override
            String getLabel() {
                return 'helmet'
            }

            @Override
            List<EquipmentType> getAllowedTypes() {
                return [TestEquipmentTypes.HELMET]
            }
        }
        PlayerStats playerStats = new PlayerStats(
            statSystem,
            [equipmentSlot],
            [(RequiredStats.MaxHP): 100]
        )
        playerStats.equipmentSlots.get(0).setEquipment(
            Equipment.builder()
                .type(TestEquipmentTypes.HELMET)
                .statAddition(RequiredStats.MaxHP, 4)
                .statMultipler(RequiredStats.MaxHP, 2f)
                .build()
        )

        expect:
        playerStats.getStatValue(RequiredStats.MaxHP) == 208
    }

    private static enum TestStats implements Stat.Key {
        MAXMP
    }

    private static enum TestEquipmentTypes implements EquipmentType {
        HELMET

        @Override
        boolean canEquip(final PlayerCharacter player) {
            return true
        }
    }
}
