package com.moonlightpixels.jrpg.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.moonlightpixels.jrpg.GdxSpecification
import com.moonlightpixels.jrpg.input.ClickEvent
import com.moonlightpixels.jrpg.input.ControlEvent
import com.moonlightpixels.jrpg.input.InputScheme

class SelectListSpec extends GdxSpecification {
    void 'Constructor throws IllegalArgumentException when item list is empty'() {
        when:
        new SelectList(Collections.emptyList(), new SelectList.SelectListStyle(Mock(Texture)))

        then:
        thrown IllegalArgumentException
    }

    void 'Constructor throws IllegalArgumentException when invalid column count given'() {
        when:
        new SelectList(
            [
                new SelectList.Item(Mock(Actor), { })
            ],
            new SelectList.SelectListStyle(Mock(Texture)),
            0
        )

        then:
        thrown IllegalArgumentException
    }

    void 'SelectList draws actors for each item'() {
        setup:
        Batch batch = Mock(Batch)
        Actor actor1 = Mock(Actor)
        Actor actor2 = Mock(Actor)
        SelectList selectList = new SelectList(
            [
                    new SelectList.Item(actor1, { }),
                    new SelectList.Item(actor2, { })
            ],
            new SelectList.SelectListStyle(Mock(Texture))
        )

        when:
        selectList.draw(batch, 1.0f)

        then:
        _ * actor1.isVisible() >> true
        1 * actor1.draw(*_)
        _ * actor2.isVisible() >> true
        1 * actor2.draw(*_)
    }

    void 'Preferred dimensions based on child content'() {
        setup:
        Actor actor = Mock(Actor)
        SelectList selectList = new SelectList(
            [
                new SelectList.Item(actor, { })
            ],
            new SelectList.SelectListStyle(Mock(Texture))
        )

        when:
        float initialPrefWidth = selectList.prefWidth
        float initialPrefHeight = selectList.prefHeight
        selectList.layout()
        float computedPrefWidth = selectList.prefWidth
        float computedPrefHeight = selectList.prefHeight

        then:
        _ * actor.getWidth() >> 20
        _ * actor.getHeight() >> 20
        initialPrefWidth == 0
        initialPrefHeight == 0
        computedPrefWidth > 0
        computedPrefHeight > 0
    }

    void 'Control event ACTION_PRESSED triggers onSelectAction'() {
        setup:
        SelectList.Action action = Mock(SelectList.Action)
        SelectList selectList = new SelectList(
            [
                new SelectList.Item(Mock(Actor), action)
            ],
            new SelectList.SelectListStyle(Mock(Texture))
        )
        selectList.setInputScheme(InputScheme.Keyboard)

        when:
        selectList.handleControlEvent(ControlEvent.ACTION_PRESSED)

        then:
        1 * action.perform()
    }

    void 'Control event navigation selects correct items in multiple column layout'() {
        setup:
        List<SelectList.Action> onCursorActions = [
                Mock(SelectList.Action),
                Mock(SelectList.Action),
                Mock(SelectList.Action),
                Mock(SelectList.Action)
        ]
        List<SelectList.Item> items = [
            new SelectList.Item(Mock(Actor), { }, onCursorActions[0]),
            new SelectList.Item(Mock(Actor), { }, onCursorActions[1]),
            new SelectList.Item(Mock(Actor), { }, onCursorActions[2]),
            new SelectList.Item(Mock(Actor), { }, onCursorActions[3])
        ]
        SelectList selectList = new SelectList(items, new SelectList.SelectListStyle(Mock(Texture)), 2)
        selectList.setInputScheme(InputScheme.Controller)

        when:
        selectList.handleControlEvent(ControlEvent.RIGHT_PRESSED)
        selectList.handleControlEvent(ControlEvent.RIGHT_RELEASED)

        then:
        1 * onCursorActions[1].perform()

        when:
        selectList.handleControlEvent(ControlEvent.DOWN_PRESSED)
        selectList.handleControlEvent(ControlEvent.DOWN_RELEASED)

        then:
        1 * onCursorActions[3].perform()

        when:
        selectList.handleControlEvent(ControlEvent.LEFT_PRESSED)
        selectList.handleControlEvent(ControlEvent.LEFT_RELEASED)

        then:
        1 * onCursorActions[2].perform()

        when:
        selectList.handleControlEvent(ControlEvent.UP_PRESSED)
        selectList.handleControlEvent(ControlEvent.UP_RELEASED)

        then:
        1 * onCursorActions[0].perform()
    }

    void 'Item can be selected by ClickEvent'() {
        setup:
        Actor actor = Mock(Actor)
        SelectList.Action action = Mock(SelectList.Action)
        SelectList selectList = new SelectList(
            [
                new SelectList.Item(actor, action)
            ],
            new SelectList.SelectListStyle(Mock(Texture))
        )
        selectList.setInputScheme(InputScheme.TouchMouse)

        when:
        boolean handled = selectList.handleClickEvent(new ClickEvent(10, 10))

        then:
        1 * actor.hit(10.0f, 10.0f, false) >> actor
        1 * action.perform()
        handled
    }

    void 'handleCLickEvent() defers to next handler if ClickEvent does not hit an item'() {
        setup:
        Actor actor = Mock(Actor)
        SelectList.Action action = Mock(SelectList.Action)
        SelectList selectList = new SelectList(
            [
                new SelectList.Item(actor, action)
            ],
            new SelectList.SelectListStyle(Mock(Texture))
        )
        selectList.setInputScheme(InputScheme.TouchMouse)

        when:
        boolean handled = selectList.handleClickEvent(new ClickEvent(10, 10))

        then:
        1 * actor.hit(*_) >> null
        0 * action.perform()
        !handled
    }

    void 'activate() and deactivate() manage state properly'() {
        setup:
        SelectList selectList = new SelectList(
            [
                new SelectList.Item(Mock(Actor), { })
            ],
            new SelectList.SelectListStyle(Mock(Texture))
        )

        when:
        selectList.deactivate()

        then:
        !selectList.isActive()

        when:
        selectList.activate()

        then:
        selectList.isActive()
    }

    void 'SelectListStyle copy constructor copies cursor'() {
        setup:
        Texture cursor = Mock(Texture)
        SelectList.SelectListStyle source = new SelectList.SelectListStyle(cursor)

        when:
        SelectList.SelectListStyle copy = new SelectList.SelectListStyle(source)

        then:
        copy.cursor == source.cursor
    }
}
