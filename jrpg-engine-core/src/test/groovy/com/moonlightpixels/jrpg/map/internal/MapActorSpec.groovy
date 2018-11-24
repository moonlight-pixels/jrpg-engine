package com.moonlightpixels.jrpg.map.internal

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.moonlightpixels.jrpg.animation.AnimationSet
import com.moonlightpixels.jrpg.map.JRPGMap
import com.moonlightpixels.jrpg.map.TileCoordinate
import com.moonlightpixels.jrpg.map.character.CharacterAnimation
import org.spockframework.gentyref.TypeToken
import spock.lang.Specification

class MapActorSpec extends Specification {
    JRPGMap map
    AnimationSet<CharacterAnimation> animationSet
    Animation<TextureRegion> animation
    TileCoordinate tileCoordinate
    Batch batch
    MapActor actor

    void setup() {
        batch = Mock()
        tileCoordinate = new TileCoordinate(1, 1)
        map = Mock(JRPGMap) {
            getTileHeight() >> 16
            getTileWidth() >> 16
            getTileCoordinateXY(tileCoordinate) >> new  Vector2(16f, 16f)
        }
        animation = Mock(Animation, type: new TypeToken<Animation<TextureRegion>>() { }.type) {
            getKeyFrame(*_) >> Mock(TextureRegion)
        }
        animationSet = Mock(AnimationSet, type: new TypeToken<AnimationSet<CharacterAnimation>>() { }.type) {
            getAnimation(*_) >> animation
            getWidth() >> 10
            getHeight() >> 24
        }
        actor = new MapActor<CharacterAnimation>(map, animationSet, tileCoordinate) {
            @Override
            protected float calculateX() {
                return calculateXForCenteredOnTile(getPosition())
            }

            @Override
            protected float calculateY() {
                return calculateYForCenteredOnTile(getPosition())
            }
        }
    }

    void 'Can correctly center actor on tile'() {
        when:
        actor.setAnimation(CharacterAnimation.StandDown, 1f)
        actor.act(0.05f)
        actor.draw(batch, 1f)

        then:
        _ * animation.getKeyFrame(*_) >> Mock(TextureRegion)
        1 * batch.draw(_ as TextureRegion, 19f, 12f)
    }

    void 'Correctly calculates centered coordinates'() {
        expect:
        actor.getCenterX() == 24
        actor.getCenterY() == 24
    }
}
