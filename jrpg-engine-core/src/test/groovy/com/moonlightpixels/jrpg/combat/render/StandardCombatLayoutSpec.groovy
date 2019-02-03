package com.moonlightpixels.jrpg.combat.render

import com.moonlightpixels.jrpg.combat.render.internal.CombatActor
import com.moonlightpixels.jrpg.combat.render.internal.CombatLayout
import com.moonlightpixels.jrpg.combat.render.internal.StandardCombatLayout
import spock.lang.Specification

class StandardCombatLayoutSpec extends Specification {
    void 'layout correctly places players'() {
        setup:
        CharacterBattleAnimationSet animationSet = new CharacterBattleAnimationSet(
            new CharacterBattleAnimationSet.Key() { } ,
            24,
            16,
            1
        )
        CombatActor player1 = new CombatActor(animationSet)
        CombatActor player2 = new CombatActor(animationSet)
        CombatLayout layout = new StandardCombatLayout(400, 240)

        when:
        layout.placePlayerCharacters([player1, player2])

        then:
        player1.x == 376
        player1.y == 180
        player2.x == 376
        player2.y == 84
    }
}
