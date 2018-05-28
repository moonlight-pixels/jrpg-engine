package com.moonlightpixels.jrpg.map.internal

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.moonlightpixels.jrpg.animation.AnimationSet
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.map.character.CharacterAnimation
import org.spockframework.gentyref.TypeToken
import spock.lang.Specification

class MapActorSpec extends Specification {
    void 'Can correctly center actor on tile'() {
        setup:
        JRPGMap map = Mock(JRPGMap)
        AnimationSet<CharacterAnimation> animationSet = Mock(
            type: new TypeToken<AnimationSet<CharacterAnimation>>() { }.type
        )
        Animation<TextureRegion> animation = Mock(type: new TypeToken<Animation<TextureRegion>>() { }.type)
        TileCoordinate tileCoordinate = new TileCoordinate(1, 1)
        Batch batch = Mock(Batch)

        when:
        Actor actor = new MapActor<CharacterAnimation>(map, animationSet, tileCoordinate) {

            @Override
            protected float calculateX() {
                return calculateXForCenteredOnTile(getPosition())
            }

            @Override
            protected float calculateY() {
                return calculateYForCenteredOnTile(getPosition())
            }
        }
        actor.setAnimation(CharacterAnimation.StandDown, 1f)
        actor.act(0.05f)
        actor.draw(batch, 1f)

        then:
        _ * map.getTileHeight() >> 16
        _ * map.getTileWidth() >> 16
        _ * map.getTileCoordinateXY(tileCoordinate) >> new  Vector2(16f, 16f)
        _ * animationSet.getWidth() >> 10
        _ * animationSet.getHeight() >> 24
        _ * animationSet.getAnimation(*_) >> animation
        _ * animation.getKeyFrame(*_) >> Mock(TextureRegion)
        1 * batch.draw(_ as TextureRegion, 19f, 12f)
    }
}
