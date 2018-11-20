package com.moonlightpixels.jrpg.story.task

import com.moonlightpixels.jrpg.map.scene.Scene
import com.moonlightpixels.jrpg.story.Task
import com.moonlightpixels.jrpg.story.event.internal.StoryEventDispatcher
import spock.lang.Specification

class SceneTaskSpec extends Specification {
    void 'task completes when scene completes'() {
        setup:
        Scene.Key scene = new Scene.Key() { }
        StoryEventDispatcher eventDispatcher = new StoryEventDispatcher()
        Task task = new SceneTask(Mock(Task.Key), scene)
        eventDispatcher.addHandler(task.eventHandler)

        when:
        eventDispatcher.sceneComplete(scene)

        then:
        task.complete
    }

    void 'task does not completes when other scene completes'() {
        setup:
        Scene.Key scene = new Scene.Key() { }
        StoryEventDispatcher eventDispatcher = new StoryEventDispatcher()
        Task task = new SceneTask(Mock(Task.Key), scene)
        eventDispatcher.addHandler(task.eventHandler)

        when:
        eventDispatcher.sceneComplete(new Scene.Key() { })

        then:
        !task.complete
    }
}
