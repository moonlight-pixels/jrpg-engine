package com.moonlightpixels.jrpg.internal.gdx

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import spock.lang.Specification

class AssetUtilSpec extends Specification {
    public static final String TEST_ASSET_PATH = 'test-assest'
    public static final Class<Texture> TEST_ASSET_TYPE = Texture
    AssetManager assetManager

    void setup() {
        assetManager = Mock(AssetManager)
    }

    void 'onDemand loads asset if not already loaded'() {
        setup:
        assetManager.isLoaded(TEST_ASSET_PATH, TEST_ASSET_TYPE) >> false

        when:
        AssetUtil.onDemand(assetManager, TEST_ASSET_PATH, TEST_ASSET_TYPE)

        then:
        1 * assetManager.load(TEST_ASSET_PATH, TEST_ASSET_TYPE)

        then:
        1 * assetManager.finishLoadingAsset(TEST_ASSET_PATH)

        then:
        1 * assetManager.get(TEST_ASSET_PATH, TEST_ASSET_TYPE)
    }

    void 'onDemand doesnt load asset if already loaded'() {
        setup:
        assetManager.isLoaded(TEST_ASSET_PATH, TEST_ASSET_TYPE) >> true

        when:
        AssetUtil.onDemand(assetManager, TEST_ASSET_PATH, TEST_ASSET_TYPE)

        then:
        0 * assetManager.load(TEST_ASSET_PATH, TEST_ASSET_TYPE)
        1 * assetManager.get(TEST_ASSET_PATH, TEST_ASSET_TYPE)
    }
}
