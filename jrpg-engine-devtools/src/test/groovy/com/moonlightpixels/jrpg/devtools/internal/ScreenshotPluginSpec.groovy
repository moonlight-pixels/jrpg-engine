package com.moonlightpixels.jrpg.devtools.internal

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.moonlightpixels.jrpg.devtools.internal.input.KeyWatcher
import com.moonlightpixels.jrpg.internal.plugin.PluginAction
import com.moonlightpixels.jrpg.internal.plugin.PluginHandlerRegistry
import spock.lang.Specification

class ScreenshotPluginSpec extends Specification {
    private Input realInput
    private Input mockInput

    void setup() {
        realInput = Gdx.input
        mockInput = Mock()
        Gdx.input = mockInput
    }

    void cleanup() {
        Gdx.input = realInput
    }

    void 'pressing screen shot key causes screen shot to be taken when plugin is installed'() {
        setup:
        PluginAction pluginAction = null
        PluginHandlerRegistry handlerRegistry = Mock(PluginHandlerRegistry) {
            registerPostRenderAction(_) >> { args ->
                pluginAction = args[0] as PluginAction
            }
        }
        mockInput.isKeyPressed(Input.Keys.NUM_1) >>> [true, false]
        KeyWatcher keyWatcher = new KeyWatcher(Input.Keys.NUM_1)
        ScreenshotTaker screenshotTaker = Mock()
        ScreenshotPlugin plugin = new ScreenshotPlugin(keyWatcher, screenshotTaker)

        when:
        plugin.init(handlerRegistry)
        pluginAction.perform()
        pluginAction.perform()

        then:
        1 * screenshotTaker.take()
     }
}
