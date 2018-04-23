package com.moonlightpixels.jrpg.ui.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.moonlightpixels.jrpg.GdxSpecification

class NinePatchUtilSpec extends GdxSpecification {
    void 'generate gradient ninepatch'() {
        setup:
        Texture texture = Mock(Texture)

        when:
        NinePatch ninePatch = NinePatchUtil.createGradientNinePatch(texture, 3, 3, 3, 3)

        then:
        _ * texture.getHeight() >> 9
        _ * texture.getWidth() >> 9
        ninePatch.middleHeight == 3.0f
        ninePatch.middleWidth == 3.0f
    }
}
