package com.moonlightpixels.jrpg.map.character

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.moonlightpixels.jrpg.map.Direction
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.map.character.internal.CharacterCommand
import com.moonlightpixels.jrpg.map.character.internal.CharacterController
import com.moonlightpixels.jrpg.map.internal.MapActor
import spock.lang.Specification

class CharacterActorSpec extends Specification {
    private JRPGMap map
    private CharacterAnimationSet animationSet
    private CharacterController controller
    private CharacterActor characterActor

    void setup() {
        map = new JRPGMap() {
            @Override
            float getTileWidth() {
                return 16
            }

            @Override
            float getTileHeight() {
                return 16
            }

            @Override
            void update(final float delta) { }

            @Override
            void render() { }

            @Override
            void addActor(final MapActor<?> actor) { }

            @Override
            boolean isOpen(final TileCoordinate tileCoordinate) {
                return true
            }
        }
        animationSet = new CharacterAnimationSet(Mock(CharacterAnimationSet.Key), 24, 16, 1)
        animationSet.addAnimationFrames(CharacterAnimation.WalkUp, [Mock(TextureRegion)] as TextureRegion[])
        animationSet.addAnimationFrames(CharacterAnimation.StandUp, [Mock(TextureRegion)] as TextureRegion[])
        controller = Mock()
        characterActor = new CharacterActor(new TileCoordinate(0, 0),
            map,
            animationSet,
            controller
            ,
            Direction.UP
        )
    }

    void 'walking moves between tiles at an even pace'(final float elapsedTime, final float expectedY) {
        setup:
        characterActor.setTimeToTraverseTile(1.0f)
        controller.getNextCommand(_) >> CharacterCommand.WalkUp
        characterActor.act(0f)

        when:
        characterActor.act(elapsedTime)

        then:
        characterActor.calculateY() == expectedY
        characterActor.calculateX() == 0

        where:
        elapsedTime | expectedY
        0.25f       | 4
        0.50f       | 8
        0.75f       | 12
        1.00f       | 16
    }
}
